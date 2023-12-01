package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.dtos.OrderFilter;
import vn.edu.iuh.fit.backend.dtos.ProductDto;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.*;
import vn.edu.iuh.fit.backend.services.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/")
    public String getAll(Model model, @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Order> orderPage = orderService.findAll(currPage - 1, pageSize, "orderDate", "desc");
        model.addAttribute("orderPage", orderPage);
        int totalPage = orderPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "order/orders";
    }


    @GetMapping("/show-form-filter")
    public ModelAndView getAllOrderFilter() {
        ModelAndView modelAndView = new ModelAndView();
        OrderFilter orderFilter = new OrderFilter();
        modelAndView.addObject("listEmployee", employeeRepository.findAll());
        modelAndView.addObject("order", orderFilter);
        modelAndView.setViewName("order/formFilter");
        return modelAndView;
    }

    @GetMapping("/filter")
    public String getOrdersFilter(@ModelAttribute("order") OrderFilter of,
                                  Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Optional<Employee> otp = employeeRepository.findById(of.getEmployeeId());
        Employee employee = null;
        LocalDate fromDate = null;
        LocalDate toDate = null;
        if(!of.getFromDate().isBlank())
            fromDate = LocalDate.parse(of.getFromDate());
        if(!of.getToDate().isBlank())
            toDate = LocalDate.parse(of.getToDate());
        if(otp.isPresent())
            employee = otp.get();
        Page<Order> orderPage = orderService.findFilter(currPage - 1, pageSize,
                "orderDate", "desc", employee, fromDate, toDate);
        model.addAttribute("orderPage", orderPage);
        int totalPage = orderPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "order/orders";
    }

    @PostMapping("/addOrder")
    public String addOrder(HttpSession session) {
        Order order = new Order();
        Random rnd = new Random();
        List<Order> orders = orderRepository.findAll();
        long orderId = orders.get(orders.size()-1).getOrder_id()+1;
        order.setOrder_id(orderId);
        order.setEmployee(employeeRepository.findById(rnd.nextLong(1,200)).get());
        order.setCustomer(customerRepository.findById(rnd.nextLong(1,200)).get());
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        Object cartObj = session.getAttribute("cart");
        if(cartObj != null) {
            Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) cartObj;
            for(Map.Entry<ProductDto, Integer> e : cart.entrySet()){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(productRepository.findById(e.getKey().getId()).get());
                orderDetail.setPrice(e.getKey().getPrice());
                orderDetail.setQuantity(e.getValue());
                orderDetailRepository.save(orderDetail);
            }
            session.invalidate();
        }
        return "redirect:/orders/";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView editOrder(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            modelAndView.addObject("order", order);
            modelAndView.setViewName("order/updateOrder");
        }
        return modelAndView;
    }

    @PostMapping("/updateOrder")
    public String updateOrder(
            @ModelAttribute("order") Order order,
            BindingResult result, Model model
    ) {
        orderRepository.save(order);
        return "redirect:/orders/";
    }
}

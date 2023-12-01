package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.repositories.OrderDetailRepository;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/order/{id}/orderDetail")
    public String getOrderDetail(@PathVariable("id") long id, Model model){
        Optional<Order> obj = orderRepository.findById(id);
        if(obj.isPresent()){
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder(obj.get());
            model.addAttribute("orderDetail", orderDetails);
        }
        return "orderDetail/orderDetail";
    }
}

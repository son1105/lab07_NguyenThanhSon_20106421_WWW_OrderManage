package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.dtos.ProductDto;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;
    @GetMapping("/")
    public String getAll(Model model, @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size){
        int currPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<ProductDto> productPage = productService.findAll(currPage-1, pageSize);
        model.addAttribute("productPage", productPage);
        int totalPage = productPage.getTotalPages();
        if(totalPage > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currPage", currPage);
        }
        return "product/products";
    }

    @GetMapping("/admin")
    public String changeInfoProductList(Model model, @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        int currPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Product> productPage = productService.findAllProduct(currPage-1, pageSize, "id", "asc");
        model.addAttribute("productPage", productPage);
        int totalPage = productPage.getTotalPages();
        if(totalPage > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "product/admin";
    }

    @GetMapping("/show-add-form")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        modelAndView.addObject("product", product);
        ProductPrice productPrice = new ProductPrice();
        modelAndView.addObject("productPrice", productPrice);
        modelAndView.setViewName("product/addProduct");
        return modelAndView;
    }

    @PostMapping("/addProduct")
    public String addProduct(
            @ModelAttribute("product") Product product,
            @ModelAttribute("productPrice") ProductPrice productPrice,
            BindingResult result, Model model
    ){
        List<Product> products = productRepository.findAll();
        product.setId(products.get(products.size()-1).getId()+1);
        product.setStatus(ProductStatus.ACTIVE);
        productRepository.save(product);
        productPrice.setProduct(product);
        productPrice.setPrice_date_time(LocalDateTime.now());
        productPriceRepository.save(productPrice);
        return "redirect:/products/admin";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView editProduct(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Product> opt = productRepository.findById(id);
        if(opt.isPresent()){
            Product product = opt.get();
            ProductPrice productPrice = new ProductPrice();
            productPrice.setProduct(product);
            modelAndView.addObject("product", product);
            modelAndView.addObject("price", productPrice);
            modelAndView.addObject("status", ProductStatus.values());
            modelAndView.setViewName("product/updateProduct");
        }
        return modelAndView;
    }

    @PostMapping("/updateProduct")
    public String updateProduct(
            @ModelAttribute("product") Product product,
            @ModelAttribute("productPrice") ProductPrice productPrice,
            BindingResult result, Model model
    ){
        productPrice.setProduct(product);
        productPrice.setPrice_date_time(LocalDateTime.now());
        productPriceRepository.save(productPrice);
        productRepository.save(product);
        return "redirect:/products/admin";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        Optional<Product> otp = productRepository.findById(id);
        if(otp.isPresent()){
            Product product = otp.get();
            product.setStatus(ProductStatus.TERMINATED);
            productRepository.save(product);
        }
        return "redirect:/products/admin";
    }

    @GetMapping("/addCart/{id}")
    public String addCart(@PathVariable("id") long id, HttpSession session, Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size){
        if(session == null)
            session = request.getSession();
        int currPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Map<ProductDto, Integer> cart;
        Optional<Product> otpProduct = productRepository.findById(id);
        if(otpProduct.isPresent()){
            Product product = otpProduct.get();
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setUnit(product.getUnit());
            productDto.setManufacturer(product.getManufacturer());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(productPriceRepository.findByProductOrderByPrice_date_time(id).get(0).getPrice());
            Object cartObj = session.getAttribute("cart");
            if(cartObj == null){
                cart = new HashMap<>();
                cart.put(productDto,1);
            }else{
                cart = (Map<ProductDto, Integer>) cartObj;
                if(cart.containsKey(productDto))
                    cart.put(productDto, cart.get(productDto)+1);
                else
                    cart.put(productDto, 1);
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/products/?size="+pageSize+"&page="+currPage;
    }

    @GetMapping("/showCart")
    public String showCart(Model model, HttpSession session){
        Object cartObj = session.getAttribute("cart");
        Map<ProductDto, Integer> cart;
        double total = 0;
        if(cartObj != null){
            cart = (Map<ProductDto, Integer>) cartObj;
            for (Map.Entry<ProductDto, Integer> c:cart.entrySet()) {
                total+=c.getKey().getPrice()*c.getValue();
            }
        } else
            cart = new HashMap<>();
        model.addAttribute("cart", cart.entrySet());
        model.addAttribute("total", total);
        return "product/cart";
    }

    @GetMapping("/showCart/deleteElement/{id}")
    public String deleteElement(@PathVariable("id") long id, HttpSession session){
        Object cartObj = session.getAttribute("cart");
        if(cartObj != null) {
            Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) cartObj;
            Product product = productRepository.findById(id).get();
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            cart.remove(dto);
            session.setAttribute("cart", cart);
        }
        return "redirect:/products/showCart";
    }
}

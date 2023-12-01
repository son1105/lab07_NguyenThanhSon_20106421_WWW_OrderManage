package vn.edu.iuh.fit;

import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;
import vn.edu.iuh.fit.backend.repositories.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

@SpringBootApplication
public class LabWeek07NguyenThanhSon20106421Application {

    public static void main(String[] args) {
        SpringApplication.run(LabWeek07NguyenThanhSon20106421Application.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Bean
    @Transactional
    CommandLineRunner createSampleProducts(OrderRepository orderRepository) {
        return args -> {
            Faker faker = new Faker(new Locale("vi", "VN"));
            Random rnd = new Random();
            Device devices = faker.device();
            // customer && employee
            for (int i = 0; i < 200; i++) {
                Customer customer = new Customer(
                        faker.name().name(), i + 1 + "@gmail.com", faker.phoneNumber().phoneNumber(),
                        faker.address().fullAddress()
                );
                customerRepository.save(customer);
                Employee employee = new Employee(
                        faker.name().fullName(), faker.date().birthday().toLocalDateTime().toLocalDate(),
                        i + 1 + "@gmail.com", faker.phoneNumber().phoneNumber(), faker.address().fullAddress(),
                        EmployeeStatus.TERMINATED
                );
                employeeRepository.save(employee);
            }

            //order
            for (int i = 0; i < 200; i++) {
                Order order = new Order(
                        faker.date().birthday().toLocalDateTime(), employeeRepository.findById(rnd.nextLong(1, 200)).get(),
                        customerRepository.findById(rnd.nextLong(1, 200)).get()
                );
                orderRepository.save(order);
            }

            for (int i = 0; i < 200; i++) {
                Product product = new Product(
                        devices.modelName(),
                        faker.lorem().paragraph(5),
//                        rnd.nextInt(10)%2==0?"Kg":"piece",
                        "piece",
                        devices.manufacturer(),
                        ProductStatus.ACTIVE
                );

                ProductImage img = new ProductImage("assets/img-sample.png", "sample image");
                img.setProduct(product);

                ProductPrice price = new ProductPrice(LocalDateTime.now(), rnd.nextInt(1500), "Note #" + i);
                price.setProduct(product);

                productRepository.save(product);
                productImageRepository.save(img);
                productPriceRepository.save(price);
            }

            // order detail
            for (int i = 0; i < 200; i++) {
                OrderDetail orderDetail = new OrderDetail(
                        rnd.nextDouble(1, 100), rnd.nextDouble(), faker.lorem().paragraph(5),
                        orderRepository.findById(rnd.nextLong(1, 200)).get(),
                        productRepository.findById(rnd.nextLong(1, 200)).get()
                );
                orderDetailRepository.save(orderDetail);
            }
        };
    }

}

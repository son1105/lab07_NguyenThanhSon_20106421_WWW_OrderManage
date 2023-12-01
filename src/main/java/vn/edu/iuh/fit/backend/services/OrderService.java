package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return orderRepository.findAll(pageable);
    }

    public Page<Order> findFilter(int pageNo, int pageSize, String sortBy, String sortDirection, Employee employee, LocalDate fromDate, LocalDate toDate) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<Order> orders;
        if (employee == null) {
            if (toDate == null)
                orders = orderRepository.findAllByOrderDateDate(fromDate);
            else
                orders = orderRepository.findAllByOrderDateDateBetween(fromDate, toDate);
        } else {
            if (toDate == null)
                orders = orderRepository.findAllByEmployeeAndOrderDate(employee, fromDate);
            else
                orders = orderRepository.findAllByEmployeeAndOrderDateBetween(employee, fromDate, toDate);
        }
        return new PageImpl<>(orders, pageable, orders.size());
    }
}

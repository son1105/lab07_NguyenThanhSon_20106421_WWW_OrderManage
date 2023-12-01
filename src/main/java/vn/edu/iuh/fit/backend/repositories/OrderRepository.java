package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where DATE(o.orderDate) = :date order by o.orderDate desc")
    List<Order> findAllByOrderDateDate(@Param("date") LocalDate date);

    @Query("select o from Order o where DATE(o.orderDate) between :fromDate and :toDate order by o.orderDate desc")
    List<Order> findAllByOrderDateDateBetween(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select o from Order o where o.employee = :employee and DATE(o.orderDate) = :date order by o.orderDate desc")
    List<Order> findAllByEmployeeAndOrderDate(@Param("employee") Employee employee, @Param("date") LocalDate date);

    @Query("select o from Order o where o.employee = :employee and DATE(o.orderDate) between :fromDate and :toDate order by o.orderDate desc")
    List<Order> findAllByEmployeeAndOrderDateBetween(@Param("employee") Employee employee, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
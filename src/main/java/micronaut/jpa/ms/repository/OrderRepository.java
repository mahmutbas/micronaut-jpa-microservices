package micronaut.jpa.ms.repository;

import micronaut.jpa.ms.model.Customer;
import micronaut.jpa.ms.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository
{
    List<Order> findAllOrdersByCustomerId(Long customerId);

    Order saveOrder(Order customerOrder, Customer customer);

    Optional<Order> findById(Long id);

    void deleteById(Long id);

    List<Order> findAll();

    int updateProductName(Long id, String productName);
}

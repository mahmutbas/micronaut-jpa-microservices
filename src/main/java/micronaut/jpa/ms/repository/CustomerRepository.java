package micronaut.jpa.ms.repository;

import micronaut.jpa.ms.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository
{
    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    void deleteById(Long id);

    List<Customer> findAll();

    int updateName(Long id, String fullname);
}

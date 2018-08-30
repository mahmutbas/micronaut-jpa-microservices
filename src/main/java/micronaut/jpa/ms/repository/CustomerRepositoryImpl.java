package micronaut.jpa.ms.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import micronaut.jpa.ms.model.Customer;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Singleton
public class CustomerRepositoryImpl implements CustomerRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public CustomerRepositoryImpl(@CurrentSession EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id)
    {
        return Optional.ofNullable(entityManager.find(Customer.class, id));
    }

    @Override
    @Transactional
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();
        newCustomer.setFullname(customer.getFullname());
        newCustomer.setCitizenNumber(customer.getCitizenNumber());
        entityManager.persist(newCustomer);
        return newCustomer;
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        findById(id).ifPresent(customer -> entityManager.remove(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll()
    {
        String sqlString = "SELECT c FROM Customer as c";
        TypedQuery query = entityManager.createQuery(sqlString, Customer.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int updateName(Long id, String fullname)
    {
        return entityManager.createQuery("UPDATE Customer c SET fullname = :fullname where id = :id")
                .setParameter("fullname", fullname)
                .setParameter("id", id)
                .executeUpdate();
    }
}

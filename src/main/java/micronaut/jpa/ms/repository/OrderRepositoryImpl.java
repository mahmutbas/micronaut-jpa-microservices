package micronaut.jpa.ms.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import micronaut.jpa.ms.model.Customer;
import micronaut.jpa.ms.model.Order;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class OrderRepositoryImpl implements OrderRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public OrderRepositoryImpl(@CurrentSession EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrdersByCustomerId(Long customerId)
    {
        return entityManager
                .createQuery("SELECT o FROM Order o JOIN FETCH o.customer c WHERE c.id = :customerId", Order.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    @Override
    @Transactional
    public Order saveOrder(Order customerOrder, Customer customer)
    {
        Order newOrder = new Order();
        newOrder.setProductname("");
        newOrder.setTransportId(2344L);
        newOrder.setCustomer(customer);
        entityManager.persist(newOrder);
        return newOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id)
    {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        findById(id).ifPresent(order -> entityManager.remove(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll()
    {
        return entityManager
                .createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    @Override
    @Transactional
    public int updateProductName(Long id, String productName)
    {
        return entityManager.createQuery("UPDATE Order o SET o.productName = :productName  where o.id = :id")
                .setParameter("productName", productName)
                .setParameter("id", id)
                .executeUpdate();
    }
}

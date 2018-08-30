package micronaut.jpa.ms.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import micronaut.jpa.ms.model.Customer;
import micronaut.jpa.ms.model.Order;
import micronaut.jpa.ms.repository.CustomerRepository;
import micronaut.jpa.ms.repository.OrderRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller("/orders")
public class OrderController
{
    protected final CustomerRepository customerRepository;
    protected final OrderRepository orderRepository;

    public OrderController(CustomerRepository customerRepository, OrderRepository orderRepository)
    {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Get("/orders/{id}")
    public List<Order> listByCustomerId(Long id)
    {
        return orderRepository.findAllOrdersByCustomerId(id);
    }

    @Put("/")
    public HttpResponse update(@Body Order order)
    {
        Optional<Order> orderOptional = orderRepository.findById(order.getId());

        return orderOptional.map(findedOrder -> {
            orderRepository.updateProductName(findedOrder.getId(), order.getProductname());
            return HttpResponse.noContent().header(HttpHeaders.LOCATION, location(findedOrder.getId()).getPath());
        }).orElse(HttpResponse.badRequest());
    }

    @Get("/")
    public List<Order> list()
    {
        return orderRepository.findAll();
    }

    @Get("/{id}")
    Order show(Long id)
    {
        return orderRepository
                .findById(id)
                .orElse(null);
    }


    @Delete("/{id}")
    HttpResponse delete(Long id)
    {
        orderRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    @Post("/")
    HttpResponse<Order> save(@Body Order order)
    {
        Optional<Customer> customerOptional = customerRepository.findById(order.getCustomer().getId());
        return customerOptional.map(customer -> {
            Order persistedOrder = orderRepository.saveOrder(order, customer);
            return HttpResponse
                    .created(persistedOrder)
                    .headers(headers -> headers.location(location(persistedOrder)));
        }).orElse(HttpResponse.badRequest());
    }


    protected URI location(Order order)
    {
        return location(order.getId());
    }

    protected URI location(Long id)
    {
        return URI.create("/orders/" + id);
    }

}

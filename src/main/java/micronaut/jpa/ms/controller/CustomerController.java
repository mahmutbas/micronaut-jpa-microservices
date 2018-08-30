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
import micronaut.jpa.ms.repository.CustomerRepository;

import java.net.URI;
import java.util.List;

@Controller("/customers")
public class CustomerController
{
    protected final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @Get("/{id}")
    public Customer show(Long id)
    {
        return customerRepository
                .findById(id)
                .orElse(null);
    }

    @Put("/")
    public HttpResponse update(@Body Customer customer)
    {
        int numberOfEntitiesUpdated = customerRepository.updateName(customer.getId(), customer.getFullname());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(customer.getId()).getPath());
    }

    @Get(value = "/")
    public List<Customer> list()
    {
        return customerRepository.findAll();
    }

    @Post("/")
    public HttpResponse<Customer> save(@Body Customer customer)
    {
        Customer persistedCustomer = customerRepository.save(customer);

        return HttpResponse
                .created(persistedCustomer)
                .headers(headers -> headers.location(location(persistedCustomer.getId())));
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id)
    {
        customerRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id)
    {
        return URI.create("/customers/" + id);
    }

    protected URI location(Customer customer)
    {
        return location(customer.getId());
    }

}

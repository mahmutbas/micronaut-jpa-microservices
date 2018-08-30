package micronaut.jpa.ms;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import micronaut.jpa.ms.model.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerTest
{
    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer()
    {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer()
    {
        if (server != null)
        {
            server.stop();
        }
        if (client != null)
        {
            client.stop();
        }
    }

    @Test
    public void testCustomerCrudOperations()
    {

        Customer customer1 = new Customer();
        customer1.setFullname("Mahmut Bas");
        customer1.setCitizenNumber(123456L);

        HttpRequest request = HttpRequest.POST("/customers", customer1);
        HttpResponse response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        Customer customer2 = new Customer();
        customer2.setFullname("Taner Yurttas");
        customer2.setCitizenNumber(3456789L);

        request = HttpRequest.POST("/customers", customer2);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.GET("/customers");
        List<Customer> customers = client.toBlocking().retrieve(request, Argument.of(List.class, Customer.class));
        System.out.println(customers.toString());
        assertEquals(2, customers.size());

        customer2.setFullname("Taner Senyurt");
        customer2.setId(customers.get(1).getId());
        request = HttpRequest.PUT("/customers", customer2);
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/customers/" + customers.get(0).getId());
        Customer firstCustomer = client.toBlocking().retrieve(request, Customer.class);
        System.out.println(firstCustomer.getFullname());
        assertEquals("Mahmut Bas", firstCustomer.getFullname());


        request = HttpRequest.DELETE("/customers/" + customers.get(0).getId());
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        System.out.println("After deletion.....");

        request = HttpRequest.GET("/customers");
        customers = client.toBlocking().retrieve(request, Argument.of(List.class, Customer.class));
        System.out.println(customers.toString());
        assertEquals(1, customers.size());

    }
}

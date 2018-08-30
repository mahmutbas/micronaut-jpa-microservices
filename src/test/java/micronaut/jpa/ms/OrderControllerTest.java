package micronaut.jpa.ms;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import micronaut.jpa.ms.model.Customer;
import micronaut.jpa.ms.model.Order;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderControllerTest
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
    public void testOrderCrudOperations()
    {

        Customer customer1 = new Customer();
        customer1.setFullname("Mahmut Bas");
        customer1.setCitizenNumber(123456L);

        HttpRequest request = HttpRequest.POST("/customers", customer1);
        HttpResponse response = client.toBlocking().exchange(request);

        request = HttpRequest.GET("/customers");
        List<Customer> customers = client.toBlocking().retrieve(request, Argument.of(List.class, Customer.class));

        Order order = new Order();
        order.setProductname("product");
        order.setTransportId(3234L);
        order.setCustomer(customers.get(0));


        request = HttpRequest.POST("/orders", order); // <3>
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());

    }
}

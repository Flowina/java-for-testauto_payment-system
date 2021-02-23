package tests;
import dao.ClientDao;
import dao.ClientDaoImpl;
import dao.ConnectionFactory;
import dao.DbConnectionFactory;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import services.ClientService;

import java.sql.Date;
import java.sql.SQLException;

public class ClientServiceTest {
    ConnectionFactory connectionFactory = new DbConnectionFactory(
            "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=PaymentSystem;",
            "java",
            "java123"
    );

    ClientDao<Integer> clientDao = new ClientDaoImpl(connectionFactory);
    ClientService<Integer> clientService = new ClientService<Integer>(clientDao);

    @BeforeClass
    public void beforeClass() {
        System.out.println("@BeforeClass");
    }

    @Test
    public void client_create__should_create_new_client() {
        Client newClient = new Client("Lara", "Croft", Date.valueOf("1959-02-14"));
        clientService.create(newClient);
        try {
            Client client = clientDao.findById(newClient.getId());
            Assert.assertTrue(client != null);
        } catch (SQLException e) {
            Assert.fail("Error creating new client", e);
        }
}

    @Test
    public void test(){
        //Assert.fail("test");
        Assert.assertTrue(true);
    }
}
/*
try {
            Client newClient = new Client("Lara", "Croft", Date.valueOf("1959-02-14"));
            clientService.create(newClient);
            clients = clientDao.getAll();
            for (Client c:
                    clients) {
                System.out.println(c);
            }

            Client client = clientDao.findById(newClient.getId());
            System.out.println(client);

            List<Client> clientsByName = clientService.findByName("ro");
            for (Client c:
                    clientsByName) {
                System.out.println(c);
            }

            client.setLastName("foo");
            clientService.update(client);
            System.out.println(client);

            clientService.delete(client);

            clients = clientDao.getAll();
            for (Client c:
                    clients) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 */
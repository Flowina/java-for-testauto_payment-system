package tests.services;
import dao.ClientDao;
import dao.ClientDaoImpl;
import dao.ConnectionFactory;
import dao.DbConnectionFactory;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import services.ClientService;
import tests.TestSettings;

import java.sql.Date;
import java.sql.SQLException;

public class ClientServiceTest {
    ClientDao clientDao;
    ClientService clientService;

    @BeforeClass
    public void beforeClass() {
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
        clientService = new ClientService(clientDao);
    }

    @Test(priority=1)
    public void client_create__should_create_new_client() {
        for (Client newClient:
                TestSettings.clients) {
            clientService.create(newClient);
            try {
                Client client = clientDao.findById(newClient.getId().get());
                Assert.assertNotNull(client);
            } catch (SQLException e) {
                Assert.fail("Error creating new client", e);
            }
        }
    }


    @Test
    public void testCreate() {
    }

    @Test(dependsOnMethods = {"client_create__should_create_new_client",})
    public void testUpdate_should_update_user() throws SQLException {
        for (Client existClient:
                TestSettings.clients) {
            String newName = existClient.getLastName() + " Jr.";
            existClient.setLastName(newName);
            clientService.update(existClient);

            Client client = clientDao.findById(existClient.getId().get());
            Assert.assertNotNull(client);
            Assert.assertEquals(client.getLastName(), newName, "Expected name = " + newName);
        }
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testTestDelete() {
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testFindByName() {
    }

    @Test
    public void testGetAll() {
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
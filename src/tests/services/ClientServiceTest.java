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

    Client[] clients = {
            new Client("Mouse", "Mikkey", Date.valueOf("1980-02-14")),
            new Client("Duck", "Donald", Date.valueOf("1959-02-14"))
    };

    @BeforeClass
    public void beforeClass() {
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
        clientService = new ClientService(clientDao);
    }

    @Test(priority=1)
    public void client_create__should_create_new_client() {
        for (Client newClient:
                clients) {
            clientService.create(newClient);
            try {
                Client client = clientDao.findById(newClient.getId().get());
                Assert.assertNotNull(client);
            } catch (SQLException e) {
                Assert.fail("Error creating new client", e);
            }
        }
    }


    @Test(dependsOnMethods = {"client_create__should_create_new_client",})
    public void testUpdate_should_update_clients() throws SQLException {
        for (Client existClient:
                clients) {
            String newName = existClient.getLastName() + " Jr.";
            existClient.setLastName(newName);
            clientService.update(existClient);

            Client client = clientService.findById(existClient.getId().get());
            Assert.assertNotNull(client);
            Assert.assertEquals(client.getLastName(), newName, "Expected name = " + newName);
        }
    }

    @Test(dependsOnMethods = {"testUpdate_should_update_clients",})
    public void testDelete__should_delete_clients() throws SQLException {
        for (Client existClient:
                clients) {

            clientService.delete(existClient);
        }
        Assert.assertTrue(clientService.getAll().size() == 0);
    }
}
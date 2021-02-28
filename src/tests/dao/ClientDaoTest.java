package tests.dao;

import dao.ClientDao;
import dao.ClientDaoImpl;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.TestSettings;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClientDaoTest {
    private ClientDao clientDao;

    @BeforeClass
    public void beforeClass() {
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
    }


    @Test(expectedExceptions = { NullPointerException.class })
    public void testCreate_client_is_null_should_throw_nullpointer_exception() throws SQLException {
        clientDao.create(null);
    }

    @Test(expectedExceptions = { SQLException.class })
    public void testCreate_client_required_field_is_null_should_throw_sql_exception() throws SQLException {
        Client cl = new Client(null, null, new Date());
        clientDao.create(cl);
    }

    @Test(priority = 1)
    public void testCreate_should_create_clients() throws SQLException {
        for (Client client:
             TestSettings.clients) {
            Assert.assertFalse(client.getId().isPresent());
            clientDao.create(client);
            Assert.assertTrue(client.getId().isPresent());

            Client createdClient = clientDao.findById(client.getId().get());

            Assert.assertEquals(client.getLastName(), createdClient.getLastName());
            Assert.assertEquals(client.getFirstName(), createdClient.getFirstName());
            Assert.assertEquals(client.getDateOfBirth(), createdClient.getDateOfBirth());
        }
    }

    @Test(priority = 2)
    public void testGetAll_should_return_all_clients() throws SQLException {
        List<Client> clients = clientDao.getAll();
        Assert.assertTrue(clients.size() > 0);
    }

    @Test(priority = 2)
    public void testFindById__should_find_client_by_id() throws SQLException {
        Client testClient = TestSettings.clients[0];
        Client client = clientDao.findById(testClient.getId().get());

        Assert.assertEquals(client.getLastName(), testClient.getLastName());
        Assert.assertEquals(client.getFirstName(), testClient.getFirstName());
        Assert.assertEquals(client.getDateOfBirth(), testClient.getDateOfBirth());
    }

    @Test(priority = 2)
    public void testFindByName__should_find_client_by_name() throws SQLException {
        String test = TestSettings.clients[0].getLastName();
        List<Client> clients = clientDao.findByName(test);
        for (Client client:
                clients) {
            Assert.assertTrue((client.getFirstName() + " " + client.getLastName()).contains(test));
        }
    }

    @Test(priority = 3)
    public void testUpdate_should_update_client() throws SQLException {
        for (Client client:
                TestSettings.clients) {
            String newLastName = client.getLastName() + "_upd";
            String newFirstName = client.getFirstName() + "_upd";
            Calendar c = Calendar.getInstance();
            c.setTime(client.getDateOfBirth());
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date newBirthday =  c.getTime();

            client.setFirstName(newFirstName);
            client.setLastName(newLastName);
            client.setDateOfBirth(newBirthday);

            clientDao.update(client);

            Client updClient = clientDao.findById(client.getId().get());

            Assert.assertEquals(updClient.getLastName(), newLastName);
            Assert.assertEquals(updClient.getFirstName(), newFirstName);
            Assert.assertEquals(updClient.getDateOfBirth(), newBirthday);
        }
    }

    @Test(priority = 3, expectedExceptions = { SQLException.class })
    public void testUpdate_required_fields_is_null__should_generate_sql_exception() throws SQLException {
        Client client = TestSettings.clients[0];
        client.setLastName(null);
        clientDao.update(client);
    }

    @Test(priority = 4)
    public void testDelete() throws SQLException {
        Client client = TestSettings.clients[0];
        long clientId = client.getId().get();
        clientDao.delete(client);

        Assert.assertNull(clientDao.findById(clientId));
    }
}

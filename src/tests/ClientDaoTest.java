package tests;

import dao.ClientDao;
import dao.ClientDaoImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.ClientService;

import java.sql.SQLException;

public class ClientDaoTest {
    private ClientDao clientDao;

    @BeforeClass
    public void beforeClass() {
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
    }

    @Test(expectedExceptions = { SQLException.class, NullPointerException.class })
    public void testCreate() throws SQLException {
        clientDao.create(null);
    }

    @Test
    public void testGetAll() {
    }

    @Test
    public void testFindById() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testFindByName() {
    }
}

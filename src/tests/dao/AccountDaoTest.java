package tests.dao;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.ClientDao;
import dao.ClientDaoImpl;
import entities.Account;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.TestSettings;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountDaoTest {
    private AccountDao accountDao;
    private ClientDao clientDao;
    private Client client;
    private Account[] accounts;

    @BeforeClass
    public void beforeClass() throws SQLException {
        accountDao = new AccountDaoImpl(TestSettings.connectionFactory);
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
        client = new Client("Dilan", "Bob", new Date());
        clientDao.create(client);

        accounts = new Account[]{
                new Account(client.getId().get(), 54321, (short) 4, 0, new Date(), null),
                new Account(client.getId().get(), 16789, (short) 4, 23.6, new Date(), null),
                new Account(client.getId().get(), 77777, (short) 4, 346.7, new Date(), null)
        };
    }


    @Test(expectedExceptions = {NullPointerException.class})
    public void testCreate_account_is_null_should_throw_nullpointer_exception() throws SQLException {
        accountDao.create(null);
    }

    @Test(priority = 1)
    public void testCreate_should_create_accounts() throws SQLException {
        for (Account account :
                accounts) {
            Assert.assertFalse(account.getId().isPresent());
            accountDao.create(account);
            Assert.assertTrue(account.getId().isPresent());

            Account createdAccount = accountDao.findById(account.getId().get());

            Assert.assertEquals(account.getClientId(), createdAccount.getClientId());
            Assert.assertEquals(account.getNumber(), createdAccount.getNumber());
            Assert.assertEquals(account.getType(), createdAccount.getType());
            Assert.assertEquals(account.getAmount(), createdAccount.getAmount());
        }
    }

    @Test(priority = 2)
    public void testGetAll_should_return_all_accounts() throws SQLException {
        List<Account> accounts = accountDao.getAll();
        Assert.assertTrue(accounts.size() == accounts.size());
    }

    @Test(priority = 2)
    public void testFindById__should_find_account_by_id() throws SQLException {
        Account testAccount = accounts[0];
        Account account = accountDao.findById(testAccount.getId().get());

        Assert.assertEquals(account.getClientId(), testAccount.getClientId());
        Assert.assertEquals(account.getNumber(), testAccount.getNumber());
        Assert.assertEquals(account.getType(), testAccount.getType());
        Assert.assertEquals(account.getAmount(), testAccount.getAmount());
    }

    @Test(priority = 2)
    public void testFindByName__should_find_account_by_client() throws SQLException {
        List<Account> accounts = accountDao.findByClient(client);
        for (Account account :
                accounts) {
            Assert.assertTrue(account.getClientId() == client.getId().get());
        }
    }

    @Test(priority = 3)
    public void testUpdate_should_update_account() throws SQLException {
        for (Account account :
                accounts) {
            int newNumber = account.getNumber() + 1;
            double newAmount = account.getAmount() * 2;

            Calendar c = Calendar.getInstance();
            c.setTime(account.getOpeningDate());
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date newOpenDate = c.getTime();
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date newCloseDate = c.getTime();

            account.setNumber(newNumber);
            account.setType((short) 2);
            account.setAmount(newAmount);
            account.setOpeningDate(newOpenDate);
            account.setClosingDate(newCloseDate);

            accountDao.update(account);

            Account updAccount = accountDao.findById(account.getId().get());

            Assert.assertEquals(account.getClientId(), updAccount.getClientId());
            Assert.assertEquals(account.getNumber(), updAccount.getNumber());
            Assert.assertEquals(account.getType(), updAccount.getType());
            Assert.assertEquals(account.getAmount(), updAccount.getAmount());
        }
    }

    @Test(priority = 4)
    public void testDelete() throws SQLException {
        Account account = accounts[0];
        long accId = client.getId().get();
        accountDao.delete(account);

        Assert.assertNull(accountDao.findById(accId));
    }
}



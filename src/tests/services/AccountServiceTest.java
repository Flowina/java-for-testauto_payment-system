package tests.services;

import dao.*;
import entities.Account;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import services.AccountService;
import services.ClientService;
import tests.TestSettings;

import java.sql.Date;
import java.util.List;

public class AccountServiceTest {
    AccountDaoImpl accountDao;
    ClientDaoImpl clientDao;
    AccountService accountService;
    ClientService clientService;
    Client client = new Client("Oreiro", "Natalia", Date.valueOf("1977-02-14"));

    Account account = null;

    @BeforeClass
    public void beforeClass() {
        accountDao = new AccountDaoImpl(TestSettings.connectionFactory);
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
        accountService = new AccountService(accountDao);
        clientService = new ClientService(clientDao);

        clientService.create(client);
        account = new Account(client.getId().get(), 54321, (short) 4, 0, new java.util.Date(), null);
    }

    @Test(priority = 1)
    public void account_create__should_create_account() {
        accountService.create(account);
        Account createdAccount = accountService.findById(account.getId().get());
        Assert.assertNotNull(createdAccount);
    }

    @Test(dependsOnMethods = "account_create__should_create_account")
    public void account_getAll__should_return_all_accounts() {
        List<Account> accounts = accountService.getAll();
        Assert.assertTrue(accounts.size() > 0);
    }

    @Test(priority = 2)
    public void account_increase_ballance() throws Exception {
        double value = 100;
        double testValue = account.getAmount() + value;
        accountService.increaseBallance(account.getId().get(), value);

        Account testAccount = accountService.findById(account.getId().get());
        Assert.assertEquals(testAccount.getAmount(), testValue);
    }

    @Test(dependsOnMethods = "account_increase_ballance")
    public void account_decrease_ballance() throws Exception {
        double value = 1;
        Account testAccount = accountService.findById(account.getId().get());
        double testValue = testAccount.getAmount() - value;
        accountService.decreaseBallance(account.getId().get(), value);

        testAccount = accountService.findById(account.getId().get());
        Assert.assertEquals(testAccount.getAmount(), testValue);
    }

    @Test(priority = 2,
            expectedExceptions = { Exception.class },
            expectedExceptionsMessageRegExp = "Can't decrease account amount. The amount can't be less than 0")
    public void account_decrease_ballance__exception_if_ballance_less_than_0() throws Exception {
        double value = account.getAmount() + 1000;
        accountService.decreaseBallance(account.getId().get(), value);
    }

    @Test(priority = 10,
        expectedExceptions = { Exception.class },
        expectedExceptionsMessageRegExp = "Can't delete account with amount greater than 0")
    public void account_delete__should_generate_exception_when_delete_with_account_greater_0() throws Exception {
        accountService.delete(account);
    }

    @Test(priority = 11,
            expectedExceptions = { Exception.class },
            expectedExceptionsMessageRegExp = "Can't delete account with amount greater than 0")
    public void account_delete__should_delete_account() throws Exception {
        long id = account.getId().get();
        account.setAmount(0);
        accountService.delete(account);
        Account test = accountService.findById(id);
        Assert.assertNull(test);
    }

    @Test(priority = 13,
            expectedExceptions = { Exception.class },
            expectedExceptionsMessageRegExp = ".+closed account")
    public void account_decrease_ballance__exception_account_is_closed() throws Exception {
        account.setClosingDate(new java.util.Date());
        accountService.update(account);
        accountService.decreaseBallance(account.getId().get(), 1000);
    }
    @Test(priority = 13,
            expectedExceptions = { Exception.class },
            expectedExceptionsMessageRegExp = ".+closed account")
    public void account_increase_ballance__exception_account_is_closed() throws Exception {
        account.setClosingDate(new java.util.Date());
        accountService.update(account);
        accountService.increaseBallance(account.getId().get(), 1000);
    }
}
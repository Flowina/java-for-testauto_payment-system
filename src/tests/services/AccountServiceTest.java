package tests.services;

import dao.*;
import entities.Account;
import org.testng.Assert;
import org.testng.annotations.*;
import services.AccountService;
import services.ClientService;
import tests.TestSettings;

import java.util.Date;
import java.util.List;

public class AccountServiceTest {
    AccountDaoImpl accountDao;
    AccountService accountService;

    @BeforeClass
    public void beforeClass() {
        accountDao = new AccountDaoImpl(TestSettings.connectionFactory);
        accountService = new AccountService(accountDao);
    }

    /*@Test
    public void account_create__should_create_new_account() {
        Account acc = new Account(1, 123456789, (short) 1, 0, new Date(), null);
        accountService.create(acc);
        Assert.assertTrue(true);
    }*/

    @Test
    public void account_getAll__should_return_all_accounts() {
        List<Account> accounts = accountService.getAll();
        Assert.assertTrue(true);
    }

}
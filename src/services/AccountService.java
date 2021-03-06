package services;

import dao.AccountDao;
import entities.Account;
import entities.Client;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AccountService  {
    private AccountDao dao;

    public AccountService(AccountDao dao) {
        this.dao = dao;
    }

    public void create (Account account) {
        try {
            dao.create(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAll () {
        try {
           return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public void update (Account account) {
        try {
            dao.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account) throws Exception {
        try {
            Account accountDB = dao.findById(account.getId().get());
            if (accountDB.getAmount() > 0) {
                throw new Exception("Can't delete account with amount greater than 0");
            }
            dao.delete(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account findById(long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void increaseBallance(long accountId, double value) throws Exception {
        try {
            Account accountDB = dao.findById(accountId);
            if (accountDB.getClosingDate() != null && accountDB.getClosingDate().before(new Date())) {
                throw new Exception("Can't increase closed account");
            }
            accountDB.setAmount(accountDB.getAmount() + value);
            dao.update(accountDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decreaseBallance(long accountId, double value) throws Exception {
        try {
            Account accountDB = dao.findById(accountId);
            if (accountDB.getClosingDate() != null && accountDB.getClosingDate().before(new Date())) {
                throw new Exception("Can't decrease closed account");
            }
            double newValue = accountDB.getAmount() - value;

            if (newValue < 0) {
                throw new Exception("Can't decrease account amount. The amount can't be less than 0");
            }

            accountDB.setAmount(newValue);
            dao.update(accountDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> findByClient(Client client) {
        try {
            return dao.findByClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
}

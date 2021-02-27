package services;

import dao.CardDao;
import entities.Account;
import entities.Card;
import entities.Client;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CardService {
    private CardDao dao;

    public CardService(CardDao dao) {
        this.dao = dao;
    }

    public void create (Card card) {
        try {
            dao.create(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card> findByAccount(Account account) {
        try {
            return dao.findByAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public List<Card> findByClient(Client client) {
        try {
            return dao.findByClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public void delete(Card card) throws Exception {
        try {
            dao.delete(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Card findById(long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

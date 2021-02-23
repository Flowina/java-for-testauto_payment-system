package services;

import dao.CardDao;
import entities.Account;
import entities.Card;
import entities.Client;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CardService<Tid> {
    private CardDao<Tid> dao;

    public CardService(CardDao<Tid> dao) {
        this.dao = dao;
    }

    public void create (Card<Tid> card) {
        try {
            dao.create(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card<Tid>> findByAccount(Account account) {
        try {
            return dao.findByAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public List<Card<Tid>> findByClient(Client client) {
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

    public Card findById(Tid id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

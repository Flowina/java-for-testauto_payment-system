package tests.dao;
import dao.*;
import entities.Account;
import entities.Card;
import entities.Client;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.TestSettings;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardDaoTest {
    private AccountDao accountDao;
    private ClientDao clientDao;
    private CardDao cardDao;
    private Client client;
    private Account account;
    private Card[] cards;

    @BeforeClass
    public void beforeClass() throws SQLException {
        accountDao = new AccountDaoImpl(TestSettings.connectionFactory);
        clientDao = new ClientDaoImpl(TestSettings.connectionFactory);
        cardDao = new CardDaoImpl(TestSettings.connectionFactory);

        client = new Client("Dilan", "Bob", new Date());
        clientDao.create(client);

        account = new Account(client.getId().get(), 54321, (short) 4, 0, new Date(), null);
        accountDao.create(account);

        cards = new Card[]{
            new Card(account.getId().get(), 1111222233334444L, "Billy Bones", (short)111, (short)2025, (short)5 ),
            new Card(account.getId().get(), 6666222233334444L, "John Silver", (short)111, (short)2030, (short)3 ),
        };
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testCreate_cards_is_null_should_throw_nullpointer_exception() throws SQLException {
        cardDao.create(null);
    }

    @Test(expectedExceptions = { SQLException.class })
    public void testCreate_card_required_field_is_null_should_throw_sql_exception() throws SQLException {
        Card card = new Card(account.getId().get(), 6666222233334444L, null, (short)111, (short)2030, (short)3 );
        cardDao.create(card);
    }

    @Test(priority = 1)
    public void testCreate_should_create_cards() throws SQLException {
        for (Card card :
                cards) {
            Assert.assertFalse(card.getId().isPresent());
            cardDao.create(card);
            Assert.assertTrue(card.getId().isPresent());

            Card createdCard = cardDao.findById(card.getId().get());

            Assert.assertEquals(card.getAccountId(), createdCard.getAccountId());
            Assert.assertEquals(card.getCardNumber(), createdCard.getCardNumber());
            Assert.assertEquals(card.getCvv(), createdCard.getCvv());
            Assert.assertEquals(card.getExpMonth(), createdCard.getExpMonth());
            Assert.assertEquals(card.getExpYear(), createdCard.getExpYear());
            Assert.assertEquals(card.getOwnerName(), createdCard.getOwnerName());
        }
    }

    @Test(priority = 2)
    public void testGetAll_should_return_all_cards() throws SQLException {
        List<Card> cards = cardDao.getAll();
        Assert.assertTrue(cards.size() == cards.size());
    }

    @Test(priority = 2)
    public void testFindById__should_find_card_by_id() throws SQLException {
        Card testCard = cards[0];
        Card card = cardDao.findById(testCard.getId().get());

        Assert.assertEquals(card.getAccountId(), testCard.getAccountId());
        Assert.assertEquals(card.getCardNumber(), testCard.getCardNumber());
        Assert.assertEquals(card.getCvv(), testCard.getCvv());
        Assert.assertEquals(card.getExpMonth(), testCard.getExpMonth());
        Assert.assertEquals(card.getExpYear(), testCard.getExpYear());
        Assert.assertEquals(card.getOwnerName(), testCard.getOwnerName());
    }

    @Test(priority = 2)
    public void testFindByName__should_find_card_by_client() throws SQLException {
        List<Card> cards = cardDao.findByClient(client);
        for (Card card :
                cards) {
            Assert.assertTrue(card.getAccountId() == account.getId().get());
            Assert.assertTrue(account.getClientId() == client.getId().get());
        }
    }

    @Test(priority = 2)
    public void testFindByName__should_find_card_by_account() throws SQLException {
        List<Card> cards = cardDao.findByAccount(account);
        for (Card card :
                cards) {
            Assert.assertTrue(card.getAccountId() == account.getId().get());
        }
    }


    @Test(priority = 3)
    public void testUpdate_should_update_card() throws SQLException {
        for (Card card :
                cards) {
            long newNumber = card.getCardNumber() + 1;
            short newCvv = (short)(card.getCvv() + 2);
            short newMonth = (short)(card.getExpMonth() + 2);
            short newYear = (short)(card.getExpYear() + 2);
            String newOwner = card.getOwnerName() + "_Upd";

            card.setCardNumber(newNumber);
            card.setCvv(newCvv);
            card.setExpMonth(newMonth);
            card.setExpYear(newYear);
            card.setOwnerName(newOwner);

            cardDao.update(card);

            Card updCard = cardDao.findById(card.getId().get());

            Assert.assertEquals(card.getAccountId(), updCard.getAccountId());
            Assert.assertEquals(card.getCardNumber(), updCard.getCardNumber());
            Assert.assertEquals(card.getCvv(), updCard.getCvv());
            Assert.assertEquals(card.getExpMonth(), updCard.getExpMonth());
            Assert.assertEquals(card.getExpYear(), updCard.getExpYear());
            Assert.assertEquals(card.getOwnerName(), updCard.getOwnerName());
        }
    }

    @Test(priority = 3, expectedExceptions = { SQLException.class })
    public void testUpdate_required_fields_is_null__should_generate_sql_exception() throws SQLException {
        Card card = cards[0];
        card.setOwnerName(null);
        cardDao.update(card);
    }


    @Test(priority = 4)
    public void testDelete() throws SQLException {
        Card card = cards[0];
        long accId = client.getId().get();
        cardDao.delete(card);

        Assert.assertNull(cardDao.findById(accId));
    }
}

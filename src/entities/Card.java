package entities;

import java.util.Date;

public class Card<Tid> {
    private Tid id;
    private int accountId;
    private int cardNumber;
    private String ownerName;
    private short cvv;
    private short expYear;
    private short expMonth;

    public Card(int accountId, int cardNumber, String ownerName, short cvv, short expYear, short expMonth) {
        this(null, accountId, cardNumber, ownerName, cvv, expYear, expMonth);
    }

    public Card(Tid id, int accountId, int cardNumber, String ownerName, short cvv, short expYear, short expMonth) {
        this.id = id;
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.ownerName = ownerName;
        this.cvv = cvv;
        this.expYear = expYear;
        this.expMonth = expMonth;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", cardNumber=" + cardNumber +
                ", ownerName='" + ownerName + '\'' +
                ", cvv=" + cvv +
                ", expYear=" + expYear +
                ", expMonth=" + expMonth +
                '}';
    }

    public Tid getId() {
        return id;
    }

    public void setId(Tid id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public short getCvv() {
        return cvv;
    }

    public void setCvv(short cvv) {
        this.cvv = cvv;
    }

    public short getExpYear() {
        return expYear;
    }

    public void setExpYear(short expYear) {
        this.expYear = expYear;
    }

    public short getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(short expMonth) {
        this.expMonth = expMonth;
    }
}





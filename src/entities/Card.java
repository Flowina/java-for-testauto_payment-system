package entities;

import java.util.Optional;

public class Card {
    private Optional<Long> id;
    private long accountId;
    private long cardNumber;
    private String ownerName;
    private short cvv;
    private short expYear;
    private short expMonth;

    public Card(long accountId, long cardNumber, String ownerName, short cvv, short expYear, short expMonth) {
        this(Optional.empty(), accountId, cardNumber, ownerName, cvv, expYear, expMonth);
    }

    public Card(Optional<Long> id, long accountId, long cardNumber, String ownerName, short cvv, short expYear, short expMonth) {
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
                "id=" + (id.isPresent() ? id.get() : "null") +
                ", accountId=" + accountId +
                ", cardNumber=" + cardNumber +
                ", ownerName='" + ownerName + '\'' +
                ", cvv=" + cvv +
                ", expYear=" + expYear +
                ", expMonth=" + expMonth +
                '}';
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getCardNumber() {
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





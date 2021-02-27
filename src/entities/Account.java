package entities;

import java.util.Date;
import java.util.Optional;

public class Account {
    /*
    CREATE TABLE [dbo].[Accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[clientId] [int] NOT NULL,
	[number] [int] NOT NULL,
	[type] [smallint] NOT NULL,
	[amount] [decimal](10, 2) NOT NULL,
	[opening_date] [datetime2](0) NOT NULL,
	[closing_date] [datetime2](0) NULL,,*/

    private Optional<Long> id;
    private long clientId;
    private int number;
    private short type;
    private double amount;
    private Date openingDate;
    private Date closingDate;

    public Account(long clientId, int number, short type, double amount, Date openingDate, Date closingDate) {
        this(Optional.empty(), clientId, number, type, amount, openingDate, closingDate);
    }

    public Account(Optional<Long> id, long clientId, int number, short type, double amount, Date openingDate, Date closingDate) {
        this.id = id;
        this.clientId = clientId;
        this.number = number;
        this.type = type;
        this.amount = amount;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
    }

    @Override
    public String toString() {
        return "id = " + (id.isPresent() ? id.get() : "null") + ": number = " + getNumber() + ": $" + getAmount() +
                ", opened: " + getOpeningDate() + ", closed: " + getClosingDate();
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }
}


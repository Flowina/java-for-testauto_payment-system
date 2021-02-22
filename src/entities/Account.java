package entities;

import java.util.Date;

public class Account<Tid> {
    /*
    CREATE TABLE [dbo].[Accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[clientId] [int] NOT NULL,
	[number] [int] NOT NULL,
	[type] [smallint] NOT NULL,
	[amount] [decimal](10, 2) NOT NULL,
	[opening_date] [datetime2](0) NOT NULL,
	[closing_date] [datetime2](0) NULL,,*/

    private Tid id;
    private int clientId;
    private int number;
    private short type;
    private double amount;
    private Date openingDate;
    private Date closingDate;

    public Account(int clientId, int number, short type, double amount, Date openingDate, Date closingDate) {
        this(null, clientId, number, type, amount, openingDate, closingDate);
    }

    public Account(Tid id, int clientId, int number, short type, double amount, Date openingDate, Date closingDate) {
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
        return getNumber() + ": " + getAmount() + ", opened: " + getOpeningDate() + ", closed: " + getClosingDate();
    }

    public Tid getId() {
        return id;
    }

    public void setId(Tid id) {
        this.id = id;
    }

    public int getClientId() {
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


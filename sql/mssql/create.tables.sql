CREATE TABLE Accounts(
	id int IDENTITY(1,1) NOT NULL,
	clientId int NOT NULL,
	number int NOT NULL,
	type smallint NOT NULL,
	amount decimal(10, 2) NOT NULL,
	opening_date datetime2(0) NOT NULL,
	closing_date datetime2(0) NULL)
GO

ALTER TABLE Accounts ADD PRIMARY KEY (id);
GO

CREATE TABLE Cards(
	id int IDENTITY(1,1) NOT NULL,
	accountId int NOT NULL,
	card_number int NOT NULL,
	owner_name varchar(510) NOT NULL,
	cvv smallint NOT NULL,
	exp_year numeric(4, 0) NOT NULL,
	exp_month smallint NOT NULL)
GO

ALTER TABLE Cards ADD PRIMARY KEY (id);
GO

CREATE TABLE Clients(
	id int IDENTITY(1,1) NOT NULL,
	lastName nvarchar(255) NOT NULL,
	firstName nvarchar(255) NOT NULL,
	dateOfBirth date NOT NULL)
GO

ALTER TABLE Clients ADD PRIMARY KEY (id);
GO

ALTER TABLE Accounts ADD  DEFAULT (NULL) FOR closing_date
GO
ALTER TABLE Accounts  WITH CHECK ADD  CONSTRAINT FK_Accounts_Clients FOREIGN KEY(clientId)
REFERENCES Clients (id)
GO
ALTER TABLE Accounts CHECK CONSTRAINT FK_Accounts_Clients
GO
ALTER TABLE Cards  WITH CHECK ADD  CONSTRAINT FK_Cards_Accounts FOREIGN KEY(accountId)
REFERENCES Accounts (id)
GO
ALTER TABLE Cards CHECK CONSTRAINT FK_Cards_Accounts
GO

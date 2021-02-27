CREATE TABLE Accounts(
	id bigint IDENTITY(1,1) NOT NULL,
	clientId bigint NOT NULL,
	number int NOT NULL,
	type smallint NOT NULL,
	amount decimal(10, 2) NOT NULL,
	opening_date datetime2(0) NOT NULL,
	closing_date datetime2(0) NULL);

ALTER TABLE Accounts ADD PRIMARY KEY (id);

CREATE TABLE Cards(
	id bigint IDENTITY(1,1) NOT NULL,
	accountId bigint NOT NULL,
	card_number bigint NOT NULL,
	owner_name varchar(510) NOT NULL,
	cvv smallint NOT NULL,
	exp_year numeric(4, 0) NOT NULL,
	exp_month smallint NOT NULL);

ALTER TABLE Cards ADD PRIMARY KEY (id);

CREATE TABLE Clients(
	id bigint IDENTITY(1,1) NOT NULL,
	lastName nvarchar(255) NOT NULL,
	firstName nvarchar(255) NOT NULL,
	dateOfBirth date NOT NULL);

ALTER TABLE Clients ADD PRIMARY KEY (id);

ALTER TABLE Accounts ADD  DEFAULT (NULL) FOR closing_date;

ALTER TABLE Accounts  WITH CHECK ADD  CONSTRAINT FK_Accounts_Clients FOREIGN KEY(clientId)
REFERENCES Clients (id);

ALTER TABLE Accounts CHECK CONSTRAINT FK_Accounts_Clients;

ALTER TABLE Cards  WITH CHECK ADD  CONSTRAINT FK_Cards_Accounts FOREIGN KEY(accountId)
REFERENCES Accounts (id);

ALTER TABLE Cards CHECK CONSTRAINT FK_Cards_Accounts;


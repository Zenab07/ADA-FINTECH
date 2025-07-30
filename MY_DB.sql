CREATE TABLE Useraccount (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    comptetype VARCHAR(20),
    login VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(10) NOT NULL
);

CREATE TABLE Wallet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE Merchant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lastname VARCHAR(50),
    firstname VARCHAR(30),
    phonenumber VARCHAR(20),
    email VARCHAR(50) UNIQUE,
    location VARCHAR(100),
    idUserAccount BIGINT NOT NULL UNIQUE,
    idWallet BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (idUserAccount) REFERENCES Useraccount(id),
    FOREIGN KEY (idWallet) REFERENCES Wallet(id)
);

CREATE TABLE Customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lastname VARCHAR(50),
    firstname VARCHAR(30),
    phonenumber VARCHAR(20),
    email VARCHAR(50) UNIQUE,
    idUserAccount BIGINT NOT NULL UNIQUE,
    idWallet BIGINT NOT NULL UNIQUE,
    gender VARCHAR(10),
    FOREIGN KEY (idUserAccount) REFERENCES Useraccount(id),
    FOREIGN KEY (idWallet) REFERENCES Wallet(id)
);

CREATE TABLE Admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lastname VARCHAR(50),
    firstname VARCHAR(30),
    phonenumber VARCHAR(20),
    email VARCHAR(50) UNIQUE,
    privileges VARCHAR(20),
    idUserAccount BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (idUserAccount) REFERENCES Useraccount(id)
);
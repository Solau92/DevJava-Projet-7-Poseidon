DROP DATABASE demo;
CREATE DATABASE demo;

USE demo;

CREATE TABLE bid (
  id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidDate TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (id)
);

CREATE TABLE trade (
  id int NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE NOT NULL ,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE curvepoint (
  id int NOT NULL AUTO_INCREMENT,
  curveId int,
  asOfDate TIMESTAMP,
  term DOUBLE ,
  cpvalue DOUBLE ,
  creationDate TIMESTAMP ,

  PRIMARY KEY (Id)
);

CREATE TABLE rating (
  id int NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125) NOT NULL ,
  sandPRating VARCHAR(125) NOT NULL ,
  fitchRating VARCHAR(125) NOT NULL ,
  orderNumber int,

  PRIMARY KEY (Id)
);

CREATE TABLE rule (
  id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(125) NOT NULL ,
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (Id)
);

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  username VARCHAR(125) NOT NULL UNIQUE,
  password VARCHAR(125) NOT NULL ,
  fullname VARCHAR(125) NOT NULL ,
  role VARCHAR(125) NOT NULL ,

  PRIMARY KEY (Id)
);

insert into users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$7qCkRp8p20d7Mv2FRv5OW.WXEA58PzlO4PHbjroK2NgrvS44jkrt6", "ADMIN");
insert into users(fullname, username, password, role) values("User", "user", "$2a$10$7qCkRp8p20d7Mv2FRv5OW.WXEA58PzlO4PHbjroK2NgrvS44jkrt6", "USER");
DROP TABLE IF EXISTS UserTypePermission;
DROP TABLE IF EXISTS UserType;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Text;
DROP TABLE IF EXISTS SocialPost;
DROP TABLE IF EXISTS Postalcode;
DROP TABLE IF EXISTS Permission;
DROP TABLE IF EXISTS Media;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS File;
DROP TABLE IF EXISTS FileType;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS CalamityAssignee;
DROP TABLE IF EXISTS Calamity;
DROP TABLE IF EXISTS BuildingType;
DROP TABLE IF EXISTS Building;
DROP TABLE IF EXISTS Alert;
DROP TABLE IF EXISTS FirebaseToken;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS CalamityPost;
DROP TABLE IF EXISTS Plan;

CREATE TABLE Alert
(
  ID              INT             NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  CreatedByUserID INT             NOT NULL,
  LocationID      INT             NOT NULL,
  CalamityID      INT             NOT NULL,
  Title           VARCHAR(200)    NOT NULL,
  Time            DATETIME        NOT NULL,
  Description     VARCHAR(1000)   NOT NULL,
  Urgency         INT DEFAULT '5' NOT NULL
);

CREATE TABLE Building
(
  ID             INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  LocationID     INT          NOT NULL,
  BuildingTypeID INT          NOT NULL,
  Postalcode     VARCHAR(10)  NOT NULL,
  Number         INT(10)      NOT NULL,
  NumberAddition VARCHAR(10)  NOT NULL,
  Description    VARCHAR(200) NOT NULL
);

CREATE TABLE BuildingType
(
  ID   INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  Naam VARCHAR(200) NOT NULL
);

CREATE TABLE Calamity
(
  ID              INT                    NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  LocationID      INT                    NOT NULL,
  CreatedByUserID INT                    NOT NULL,
  isConfirmed     TINYINT(1) DEFAULT '0' NOT NULL,
  isClosed        TINYINT(1) DEFAULT '0' NOT NULL,
  Time            DATETIME               NOT NULL,
  Title           VARCHAR(200)           NOT NULL,
  Message         VARCHAR(1000)          NOT NULL
);

CREATE TABLE CalamityAssignee
(
  ID         INT NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  CalamityID INT NOT NULL,
  AssigneeID INT NOT NULL
);

CREATE TABLE Comment
(
  ID              INT           NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  CreatedByUserID INT           NOT NULL,
  AlertID         INT           NOT NULL,
  Title           VARCHAR(200)  NOT NULL,
  Message         VARCHAR(1000) NOT NULL
);

CREATE TABLE File
(
  ID       INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  MediaID  INT          NOT NULL,
  FileName VARCHAR(100) NOT NULL,
  FileType VARCHAR(100) NOT NULL
);

CREATE TABLE Location
(
  ID        INT                                    NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  Latitude  FLOAT(20, 10) DEFAULT '51.4516906738'  NOT NULL,
  Longitude FLOAT(20, 10) DEFAULT '5.4815969467'   NOT NULL,
  Radius    FLOAT(20, 10) DEFAULT '100.0000000000' NOT NULL
);

CREATE INDEX Longitude
  ON Location (Longitude);

CREATE TABLE Media
(
  ID      INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  AlertID INT          NOT NULL,
  Name    VARCHAR(200) NOT NULL
);

CREATE TABLE Text
(
  ID      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  MediaID INT NOT NULL,
  Text    VARCHAR(2000)
);

CREATE TABLE Permission
(
  ID          INT           NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  Node        VARCHAR(200)  NOT NULL,
  Description VARCHAR(1000) NOT NULL
);

CREATE TABLE Postalcode
(
  PostcodeID      INT         NOT NULL
    PRIMARY KEY,
  PostCodePK      VARCHAR(12) NULL,
  PostCode        VARCHAR(12) NULL,
  PostcodeNummers INT         NOT NULL,
  PostcodeLetters VARCHAR(5)  NULL,
  Straat          VARCHAR(60) NULL,
  MinNummer       INT         NOT NULL,
  MaxNummer       INT         NOT NULL,
  Plaats          VARCHAR(60) NULL,
  Gemeente        VARCHAR(60) NULL,
  Provincie       VARCHAR(60) NULL,
  Latitude        DOUBLE      NULL,
  Longitude       DOUBLE      NULL
);

CREATE INDEX MaxNummer_index
  ON Postalcode (MaxNummer);

CREATE INDEX MinNummer_index
  ON Postalcode (MinNummer);

CREATE INDEX PostcodeNummers_index
  ON Postalcode (PostcodeNummers);

CREATE TABLE SocialPost
(
  ID       INT           NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  TextID   INT           NOT NULL,
  VisualID INT           NOT NULL,
  Title    VARCHAR(200)  NOT NULL,
  Platform VARCHAR(200)  NOT NULL,
  Text     VARCHAR(1000) NOT NULL
);

CREATE TABLE User
(
  ID              INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  UserTypeID      INT          NOT NULL,
  BuildingID      INT          NOT NULL,
  Username        VARCHAR(128) NOT NULL,
  PasswordHash    VARCHAR(256) NOT NULL,
  Salt            VARCHAR(64)  NOT NULL,
  Email           VARCHAR(128) NOT NULL,
  City            VARCHAR(128) NOT NULL,
  Token           VARCHAR(64)  NOT NULL,
  TokenExpiration VARCHAR(45)  NOT NULL,

  CONSTRAINT Username_UNIQUE
  UNIQUE (Username),
  CONSTRAINT Token_UNIQUE
  UNIQUE (Token)
);

CREATE TABLE UserType
(
  ID   INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  Naam VARCHAR(200) NOT NULL
);

CREATE TABLE UserTypePermission
(
  ID           INT NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  UserTypeID   INT NOT NULL,
  PermissionID INT NOT NULL
);

CREATE TABLE FirebaseToken
(
  ID            INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  UserID        INT          NOT NULL,
  FirebaseToken VARCHAR(250) NOT NULL,

  CONSTRAINT UserID_UNIQUE
  UNIQUE (UserID),
  CONSTRAINT FirebaseToken_UNIQUE
  UNIQUE (FirebaseToken)
);

CREATE TABLE CalamityPost
(
  CalamityID INT NOT NULL,
  PostID     INT NOT NULL
);

CREATE TABLE Post
(
  ID     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  UserID INT NOT NULL,
  Text   VARCHAR(2000)
);

CREATE TABLE Plan
(
  ID          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CalamityID  INT NOT NULL UNIQUE,
  Description VARCHAR(2000),

  CONSTRAINT CalamityID_UNIQUE
  UNIQUE (CalamityID)
);

INSERT INTO UserType (Naam) VALUES ('Administrator');
INSERT INTO UserType (Naam) VALUES ('Hulpverlener');
INSERT INTO UserType (Naam) VALUES ('Guest');

INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_ADD', 'Permissions to add a Calamity');
INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_UPDATE', 'Permissions to update a Calamity');
INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_GET', 'Permissions to get a Calamity');
INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_DELETE', 'Permissions to delete a Calamity');
INSERT INTO Permission (Node, Description) VALUES ('USER_REGISTER', 'Permissions to register an User');
INSERT INTO Permission (Node, Description) VALUES ('USER_DELETE', 'Permissions to delete an User');
INSERT INTO Permission (Node, Description) VALUES ('USER_UPDATE', 'Permissions to update an User');
INSERT INTO Permission (Node, Description)
VALUES ('CALAMITY_ADD_ASSIGNEE', 'Permissions to add an Assignee to a Calamity');
INSERT INTO Permission (Node, Description)
VALUES ('CALAMITY_DELETE_ASSIGNEE', 'Permissions to remove an Assignee to a Calamity');
INSERT INTO Permission (Node, Description) VALUES ('ALERT_ADD', 'Permissions to add an alert');
INSERT INTO Permission (Node, Description) VALUES ('ALERT_UPDATE', 'Permissions to update an alert');
INSERT INTO Permission (Node, Description) VALUES ('ALERT_GET', 'Permissions to get an alert');
INSERT INTO Permission (Node, Description) VALUES ('ALERT_DELETE', 'Permissions to delete an alert');
INSERT INTO Permission (Node, Description)
VALUES ('USER_NOTIFY', 'Permission to notify a user to give the operator more information');
INSERT INTO Permission (Node, Description)
VALUES ('SET_FIREBASE_TOKEN', 'Permission to login into a device and make it ready to get notified');
INSERT INTO Permission (Node, Description) VALUES ('DUMMY', 'DUMMY');
INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_POST', 'Permission to add post to calamity');
INSERT INTO Permission (Node, Description) VALUES ('CALAMITY_PLAN', 'Permission to add plan to calamity');
INSERT INTO Permission (Node, Description) VALUES ('SOCIAL_POST_GET', 'Permission to get list of social posts');

INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 1);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 2);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 3);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 4);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 5);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 6);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 7);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 8);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 9);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 10);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 11);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 12);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 13);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 14);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 15);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 17);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 18);
INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (1, 19);

INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (2, 16);

INSERT INTO UserTypePermission (UserTypeID, PermissionID) VALUES (3, 16);

INSERT INTO User (UserTypeID, BuildingID, Username, PasswordHash, Salt, Email, City, Token, TokenExpiration) VALUES
  (1, -1, 'Henk', '72730eb1ef6b8619ce0df9a9f8fb2a693e551a86fdacd0779ffe331a75fa6264',
   'f0e24d7144ff2acf2661f5e8482d63ef0234563e71dc686252645be2e505e646', 'Henk@securoserve.nl', 'EHV',
   '05c127412149c846f89156f52825ff1423d17f7c611c3358fe3c26c0b1d0dde2', '2017/05/29 08:21:35');
INSERT INTO User (UserTypeID, BuildingID, Username, PasswordHash, Salt, Email, City, Token, TokenExpiration) VALUES
  (1, -1, 'Jan', 'e8eb7a6666d28bfdbc6bdc2e56c1ded85e6b997800c2308583b85432720915ba',
   '9a442aa03145af72da026b681cb027428f567b8caea5bd95ecb9eb61a4814477', 'Jan@securoserve.nl', 'EHV',
   '838010ae6dd82a503f1253220d3624e3222b65416be4b9f657c16ebdafeec39b', '2017/05/29 10:00:54');
INSERT INTO User (UserTypeID, BuildingID, Username, PasswordHash, Salt, Email, City, Token, TokenExpiration) VALUES
  (1, -1, 'Piet', '6b4ae9e7d6db1f1705816558ee3f6fb3cf32b1ce34488dffb4f12d7e374f8120',
   '2151dae12c5f391a87ae827d4365dea2e68d3a891b4b0b1c7f2dce6b82cdfc0e', 'Piet@securoserve.nl', 'EHV',
   'ad60250c3226f7537c374a4783b907f59d96846a263cbb5dca3a6830ffbc524e', '2017/05/29 04:30:17');
INSERT INTO User (UserTypeID, BuildingID, Username, PasswordHash, Salt, Email, City, Token, TokenExpiration) VALUES
  (1, -1, 'Ellyie', '4c1b086d1d1f7c71a79f3ce073d405a1fb92f8467940e8458b81de6bb8bee131',
   '416b4d356e0b44a98a819617f298240154b43fb0a001fe727ca2dc4e79fba04f', 'Ellyie@securoserve.nl', 'EHV',
   'b7a8a01e7425b008eb91aea61055107c3a73f7d5576f380037d1832421f6b58d', '2017/05/29 10:00:54');

INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.188217, 6.00973, 3000);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (52.191586, 5.498432, 3500);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.249237, 5.704538, 1500);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (52.079597, 4.313273, 10000);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.554985, 5.081718, 2500);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (52.367027, 6.668492, 2500);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.084126, 5.80753, 25);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.142124, 5.933264, 250);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (52.038765, 5.666199, 25);
INSERT INTO Location (Latitude, Longitude, Radius) VALUES (51.13204, 6.073557, 1000);


INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (1, 1, 1, 0, '2001-09-11 00:00:00', 'Calamiteit op de donderberg', 'Korte beschrijving:

In Roermond is een atoombom gevallen, getuigen in naastliggende dorpen hebben Japanse vliegtuigen gezien. Er wordt getwijfeld of er in Roermond overlevenden zijn, momenteel is het een onbegaanbaar gebied.

Aantal doden: ±57.000
Aantal gewonden: ±2000
Tijdstip ongeval: 09:11');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message) VALUES
  (2, 1, 1, 0, '2017-05-11 00:00:00', 'Loslopend schaap',
   'Groot nieuws vandaag in schubbekutteveen, er is een schaap losgebroken, het schaap rent momenteel waarschijnlijk richting de snelweg. Er is een grootscheepse zoekactie op touw gezet!');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message) VALUES
  (3, 1, 1, 0, '2017-05-22 00:00:00', 'Gasaanval',
   'Er is een gasaanval gaande op Weert station, de geallieerden hebben zich verdaan en denken dat het vol met nazi''s zit!');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (4, 1, 1, 0, '2017-05-15 16:28:19', 'Staatsgreep', 'Greet is een staatsgreep aan het plegen');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (5, 1, 1, 0, '2017-05-22 00:00:00', 'Ongeluk met vrachtwagen in Tilburg', 'Korte beschrijving:

Op de Tilburgse promenade is een ongeluk met vrachtwagen gebeurt, het gaat hier mogelijk om een terreurdaad. Een vrachtwagen is over de promenade gereden en heeft verschillende bezoekers geraakt, hieronder zijn vele doden en gewonden. Het inmiddels rustigerbdbdbd

Aantal doden: ±37
Aantal gewonden: ±200
Tijdstip ongeval: 13:37');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message) VALUES
  (6, 1, 1, 0, '2017-05-22 19:11:19', 'Lorem ipsum dolor sit amet',
   'consectetur adipiscing elit. Nulla congue semper ex id viverra. Nullam placerat quam nec mauris elementum vestibulum. Sed eu posuere erat. Curabitur sed urna in enim rhoncus convallis nec id massa. Aenean sed massa in mi sagittis scelerisque. Quisque euismod mollis imperdiet. Aliquam quis volutpat tortor. Fusce non mauris eget orci maximus congue. Maecenas suscipit lacinia erat non scelerisque. Aenean varius leo quis tellus lobortis, ornare convallis lorem suscipit. Nulla venenatis ornare quam. Sed non eros ut sem facilisis egestas. Nulla commodo est non risus condimentum, nec lobortis risus venenatis. Donec vel enim mi.');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (7, 1, 1, 0, '2017-05-22 09:11:19', 'Drie auto''s betrokken bij botsing in Roosteren', 'Drie auto''s waren maandagochtend betrokken bij een verkeersongeluk in Roosteren.
De kopstaartbotsing gebeurde even na 8:00 uur op de Maaseikerweg. De oorzaak is nog niet duidelijk.
Eén inzittende kwam bekneld te zitten in zijn auto. Voor zover bekend is niemand noemenswaardig gewond geraakt.');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (8, 1, 1, 0, '2017-05-23 04:01:19', 'Brandweer rukt uit voor brand op milieupark', 'Op het milieupark in Montfort heeft dinsdagochtend in alle vroegte een brand gewoed.
Rond 4:00 uur kreeg de brandweer een melding van de brand.
Omdat de vlammen op het milieupark aan de Maasbrachterweg vanaf de A73 te zien waren, werd er uitgegaan van een grote brand. De brandweer rukte dus uit met groot materieel uit.
Ter plekke bleek het om een kleine brand te gaan die waarschijnlijk door broei is ontstaan. Het vuur was snel gedoofd.');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (9, 1, 1, 1, '2017-05-20 00:00:00', 'Forse woningbrand', 'De brandweer van Groningen is zaterdagavond met meerdere voertuigen uitgerukt naar de Orchideestraat in Groningen voor een woningbrand.
Bij aankomst van de brandweer kwam er veel rook uit de woning. Op het moment van de brand was er niemand thuis.
 
Door een snelle brandweerinzet kon het vuur snel worden geblust. Stichting Salvage komt ter plaatse voor de afhandeling van de brand en rookschade aan drie woningen.');
INSERT INTO Calamity (LocationID, CreatedByUserID, isConfirmed, isClosed, Time, Title, Message)
VALUES (10, 1, 1, 0, '2017-05-20 00:00:00', 'Oplichter gevonden en getatoeëerd', 'Kaartjes oplichter Stan T. is vandaag gevonden
in zijn woonplaats. In het limburgse vlodrop
bleek hij ook nog een satudarah fan te zijn 
en heeft hij een prachtige tatoeage laten 
zetten. Inmiddels kan hij ervan genieten 
in de bak!');
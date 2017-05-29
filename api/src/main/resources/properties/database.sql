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
  ID            INT NOT NULL AUTO_INCREMENT
                PRIMARY KEY,
  UserID        INT NOT NULL,
  FirebaseToken VARCHAR(250) NOT NULL,

  CONSTRAINT UserID_UNIQUE
  UNIQUE (UserID),
  CONSTRAINT FirebaseToken_UNIQUE
  UNIQUE (FirebaseToken)
);

INSERT INTO UserType (Naam) VALUES ('Administrator');
INSERT INTO UserType (Naam) VALUES ('Hulpverlener');

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
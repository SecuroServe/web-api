DROP TABLE UserTypePermission;
DROP TABLE UserType;
DROP TABLE User;
DROP TABLE Text;
DROP TABLE SocialPost;
DROP TABLE Postalcode;
DROP TABLE Permission;
DROP TABLE Media;
DROP TABLE Location;
DROP TABLE FileType;
DROP TABLE File;
DROP TABLE Comment;
DROP TABLE CalamityAssignee;
DROP TABLE Calamity;
DROP TABLE BuildingType;
DROP TABLE Building;
DROP TABLE Alert;

CREATE TABLE Alert
(
  ID              INT             NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  CreatedByUserID INT             NOT NULL,
  LocationID      INT             NOT NULL,
  CalamityID      INT             NOT NULL,
  Title           VARCHAR(200)    NOT NULL,
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
  ID         INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  FileTypeID INT          NOT NULL,
  MediaID    INT          NOT NULL,
  Title      VARCHAR(200) NOT NULL,
  FileName   VARCHAR(100) NOT NULL
);

CREATE TABLE FileType
(
  ID        INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  Name      VARCHAR(200) NOT NULL,
  Extension VARCHAR(10)  NOT NULL,
  MimeType  VARCHAR(100) NOT NULL
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
  ID      INT NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  AlertID INT NOT NULL
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

CREATE TABLE Text
(
  ID          INT           NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  MediaID     INT           NOT NULL,
  Title       VARCHAR(200)  NOT NULL,
  Description VARCHAR(1000) NOT NULL
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

INSERT INTO securoserve.UserType (Naam) VALUES ('Administrator');

INSERT INTO securoserve.Permission (Node, Description) VALUES ('CALAMITY_ADD', 'Permissions to add a Calamity');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('CALAMITY_UPDATE', 'Permissions to update a Calamity');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('CALAMITY_GET', 'Permissions to get a Calamity');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('CALAMITY_DELETE', 'Permissions to delete a Calamity');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('USER_REGISTER', 'Permissions to register an User');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('USER_DELETE', 'Permissions to delete an User');
INSERT INTO securoserve.Permission (Node, Description) VALUES ('USER_UPDATE', 'Permissions to update an User');
INSERT INTO securoserve.Permission (Node, Description)
VALUES ('CALAMITY_ADD_ASSIGNEE', 'Permissions to add an Assignee to a Calamity');
INSERT INTO securoserve.Permission (Node, Description)
VALUES ('CALAMITY_DELETE_ASSIGNEE', 'Permissions to remove an Assignee to a Calamity');

INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 1);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 2);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 3);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 4);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 5);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 6);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 7);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 8);
INSERT INTO securoserve.UserTypePermission (UserTypeID, PermissionID) VALUES (1, 9);
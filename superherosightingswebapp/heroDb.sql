DROP DATABASE IF EXISTS heroDb;
CREATE DATABASE heroDb;
USE heroDb;

CREATE TABLE superpower (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE superhero (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image BLOB,
    superpowerId INT NOT NULL
);

CREATE TABLE organization (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(100) NOT NULL,
    contactNumber CHAR(10) NOT NULL
);

CREATE TABLE superhero_organization (
    superheroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY (superheroId, organizationId),
    FOREIGN KEY (superheroId) REFERENCES superhero (id),
    FOREIGN KEY (organizationId) REFERENCES organization (id)
);

CREATE TABLE location (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(100) NOT NULL,
    latitude DECIMAL(6,4) NOT NULL,
    longitude DECIMAL(7,4) NOT NULL
);

CREATE TABLE sighting (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    superheroId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (superheroId) REFERENCES superhero (id),
    FOREIGN KEY (locationId) REFERENCES location (id)
);

DROP DATABASE IF EXISTS heroDbTest;
CREATE DATABASE heroDbTest;
USE heroDbTest;

CREATE TABLE superpower (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE superhero (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image BLOB,
    superpowerId INT NOT NULL
);

CREATE TABLE organization (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(100) NOT NULL,
    contactNumber CHAR(10) NOT NULL
);

CREATE TABLE superhero_organization (
    superheroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY (superheroId, organizationId),
    FOREIGN KEY (superheroId) REFERENCES superhero (id),
    FOREIGN KEY (organizationId) REFERENCES organization (id)
);

CREATE TABLE location (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(100) NOT NULL,
    latitude DECIMAL(6,4) NOT NULL,
    longitude DECIMAL(7,4) NOT NULL
);

CREATE TABLE sighting (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    superheroId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (superheroId) REFERENCES superhero (id),
    FOREIGN KEY (locationId) REFERENCES location (id)
);

-- Ensure schema exists and use it (Compose also creates it via MYSQL_DATABASE)
CREATE DATABASE IF NOT EXISTS aeroparker
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE aeroparker;

CREATE TABLE IF NOT EXISTS customers (
    id               INT UNSIGNED NOT NULL AUTO_INCREMENT,
    registered       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email_address    VARCHAR(255) NOT NULL,
    title            VARCHAR(5)   NOT NULL,
    first_name       VARCHAR(50)  NOT NULL,
    last_name        VARCHAR(50)  NOT NULL,
    address_line_1   VARCHAR(255) NOT NULL,
    address_line_2   VARCHAR(255) NULL,
    city             VARCHAR(255) NULL,
    postcode         VARCHAR(10)  NOT NULL,
    phone_number     VARCHAR(20)  NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_customers_email (email_address)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

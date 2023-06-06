CREATE SEQUENCE city_seq;
CREATE TABLE cities (
                        city_id INT DEFAULT NEXTVAL ('city_seq') PRIMARY KEY,
                        city VARCHAR(255) NOT NULL
);

CREATE SEQUENCE states_seq;
CREATE TABLE states (
                        state_id INT DEFAULT NEXTVAL ('states_seq') PRIMARY KEY,
                        state VARCHAR(255) NOT NULL,
                        uf VARCHAR(2) NOT NULL
);

CREATE SEQUENCE address_seq;
CREATE TABLE address(
                        address_id INT DEFAULT NEXTVAL ('address_seq') PRIMARY KEY,
                        street VARCHAR(255) NOT NULL,
                        number INT NOT NULL,
                        district VARCHAR(100) NOT NULL,
                        zip_code VARCHAR(100),
                        city_id INT NOT NULL,
                        state_id INT NOT NULL,

                        CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id),
                        CONSTRAINT FK_state_id FOREIGN KEY (state_id) REFERENCES states(state_id)
);

CREATE SEQUENCE users_seq;
CREATE TABLE users (
                       user_id INT DEFAULT NEXTVAL ('users_seq') PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL unique,
                       password VARCHAR(255) NOT NULL,
                       sex VARCHAR(1) NOT NULL,
                       legal_document VARCHAR(11) NOT NULL,
                       address_id INT NOT NULL,
                       birth_date TIMESTAMP(0) NULL DEFAULT NULL,
                       phone1 VARCHAR(100) NOT NULL,
                       phone2 VARCHAR(100) NOT NULL,

                       created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                       deleted_at TIMESTAMP(0) NULL DEFAULT NULL,

                       CONSTRAINT FK_address_id FOREIGN KEY (address_id) REFERENCES address(address_id)

);

CREATE SEQUENCE country_code_seq;
CREATE TABLE country_code(
                             country_code_id INT DEFAULT NEXTVAL('country_code_seq') PRIMARY KEY,
                             country_code INT NOT NULL UNIQUE
);

CREATE SEQUENCE phones_seq;
CREATE TABLE phones(
                       phones_id INT DEFAULT NEXTVAL ('phones_seq') PRIMARY KEY,
                       phone VARCHAR(12) NOT NULL,
                       country_code_id INT NOT NULL,
                       user_id int not null,

                       CONSTRAINT FK_users_id FOREIGN KEY (user_id) REFERENCES users(user_id),
                       CONSTRAINT FK_country_code FOREIGN KEY (country_code_id) REFERENCES country_code(country_code_id)
);


CREATE SEQUENCE roles_seq;
CREATE TABLE roles (
                       roles_id INT DEFAULT NEXTVAL ('roles_seq') PRIMARY KEY,
                       name varchar(45) NOT NULL UNIQUE,

                       created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                       deleted_at TIMESTAMP(0) NULL DEFAULT NULL
);


CREATE SEQUENCE user_roles_seq;
CREATE TABLE user_roles (
                    user_roles_id INT DEFAULT NEXTVAL ('user_roles_seq') PRIMARY KEY,
                    user_id INT CHECK (user_id > 0) NOT NULL,
                    role_id INT CHECK (role_id > 0) NOT NULL,

                    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                    deleted_at TIMESTAMP(0) NULL DEFAULT NULL,

                    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
                    CONSTRAINT FK_role_id FOREIGN KEY (role_id) REFERENCES roles(roles_id)
);

CREATE SEQUENCE vehicles_seq;
CREATE TABLE vehicles (
                          vehicles_id INT DEFAULT NEXTVAL ('vehicles_seq') PRIMARY KEY,
                          name VARCHAR(250) NOT NULL ,
                          chassi VARCHAR(50) NOT NULL ,
                          license_plate VARCHAR(50) NOT NULL,
                          manufacturer VARCHAR(50) NOT NULL,
                          manufactured_year VARCHAR(50) NOT NULL,
                          legal_document VARCHAR (50) NOT NULL,

                          created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                          deleted_at TIMESTAMP(0) NULL DEFAULT NULL

);

CREATE SEQUENCE rents_seq;
CREATE TABLE rents (
                          rents_id INT DEFAULT NEXTVAL ('rents_seq') PRIMARY KEY,
                          creator_id INT CHECK (creator_id > 0) NOT NULL,
                          attendant_id INT CHECK (creator_id > 0) NOT NULL,
                          vehicles_id INT CHECK (vehicles_id > 0) NOT NULL,
                          status VARCHAR(50),
                          withdraw_date TIMESTAMP(0),
                          return_date TIMESTAMP(0),
                          price INT NOT NULL,
                          payment_Status VARCHAR(50),


                          created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                          deleted_at TIMESTAMP(0) NULL DEFAULT NULL,

                          CONSTRAINT FK_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id),
                          CONSTRAINT FK_attendant_id FOREIGN KEY (attendant_id) REFERENCES users(user_id),
                          CONSTRAINT FK_vehicles_id FOREIGN KEY (vehicles_id) REFERENCES vehicles(vehicles_id)


);



CREATE SEQUENCE rent_contracts_seq;
CREATE TABLE rent_contracts (
                          rent_contracts_id INT DEFAULT NEXTVAL ('rent_contracts_seq') PRIMARY KEY,
                          vehicles_id INT CHECK (vehicles_id > 0) NOT NULL,
                          rents_id INT CHECK (rents_id > 0) NOT NULL,
                          contract_type VARCHAR (10),
                          file_path VARCHAR (50),
                          contract_number VARCHAR(50),
                          contract_description VARCHAR(250),

                          created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
                          deleted_at TIMESTAMP(0) NULL DEFAULT NULL,

                          CONSTRAINT FK_vehicles_id FOREIGN KEY (vehicles_id) REFERENCES vehicles(vehicles_id),
                          CONSTRAINT FK_rents_id FOREIGN KEY (rents_id) REFERENCES rents(rents_id)

);

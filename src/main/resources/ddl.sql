DROP TABLE IF EXISTS users;
CREATE TABLE users(
                      id uuid DEFAULT random_uuid() PRIMARY KEY,
                      name VARCHAR(200) not null,
                      email VARCHAR(60) NOT NULL,
                      password VARCHAR(60) NOT NULL,
                      active BOOLEAN,
                      created TIMESTAMP,
                      modified TIMESTAMP,
                      last_login TIMESTAMP
);

DROP TABLE IF EXISTS token_authorization;

CREATE TABLE token_authorization(
                             id INT AUTO_INCREMENT  PRIMARY KEY,
                             token VARCHAR(200) NOT NULL
);


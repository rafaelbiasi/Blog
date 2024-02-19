CREATE TABLE IF NOT EXISTS entity
(
    type     VARCHAR(31) NOT NULL,
    id       BIGINT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id         BIGINT       NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES entity (id)
);

CREATE TABLE IF NOT EXISTS post
(
    id              BIGINT       NOT NULL UNIQUE,
    code            VARCHAR(255) NOT NULL UNIQUE,
    title           VARCHAR(255) NOT NULL,
    body            TEXT,
    image_file_path VARCHAR(255),
    account_id      BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (id) REFERENCES entity (id)
);

CREATE TABLE IF NOT EXISTS role
(
    id       BIGINT    NOT NULL UNIQUE,
    name     VARCHAR(16) UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES entity (id)
);

CREATE TABLE IF NOT EXISTS account_role
(
    account_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

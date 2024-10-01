CREATE TABLE `entity`
(
    `type`     varchar(31) NOT NULL,
    `id`       bigint      NOT NULL,
    `creation` datetime(6) NOT NULL,
    `modified` datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `account`
(
    `email`      varchar(255) NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name`  varchar(255) NOT NULL,
    `password`   varchar(255) NOT NULL,
    `username`   varchar(255) NOT NULL,
    `id`         bigint       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_account_email` (`email`),
    UNIQUE KEY `UK_account_username` (`username`),
    CONSTRAINT `CFK_account_id` FOREIGN KEY (`id`) REFERENCES `entity` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `post`
(
    `body`            text,
    `image_file_path` varchar(255) DEFAULT NULL,
    `slugified_title` varchar(255) NOT NULL,
    `title`           varchar(255) NOT NULL,
    `id`              bigint       NOT NULL,
    `author_id`       bigint       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_post_slugified_title` (`slugified_title`),
    KEY `FK_post_author_id` (`author_id`),
    CONSTRAINT `CFK_post_id` FOREIGN KEY (`id`) REFERENCES `entity` (`id`),
    CONSTRAINT `CFK_post_author_id` FOREIGN KEY (`author_id`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `role`
(
    `name` varchar(16) NOT NULL,
    `id`   bigint      NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_role_id` FOREIGN KEY (`id`) REFERENCES `entity` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `account_role`
(
    `account_id` bigint NOT NULL,
    `role_id`    bigint NOT NULL,
    PRIMARY KEY (`account_id`, `role_id`),
    KEY `FK_account_role_role_id` (`role_id`),
    CONSTRAINT `CFK_account_role_account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `CFK_account_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `comment`
(
    `text`       varchar(255) NOT NULL,
    `id`         bigint       NOT NULL,
    `account_id` bigint       NOT NULL,
    `post_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_comment_account_id` (`account_id`),
    KEY `FK_comment_post_id` (`post_id`),
    CONSTRAINT `CFK_comment_id` FOREIGN KEY (`id`) REFERENCES `entity` (`id`),
    CONSTRAINT `CFK_comment_account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `CFK_comment_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;


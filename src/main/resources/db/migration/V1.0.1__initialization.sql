DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;
DROP TABLE IF EXISTS neighbor_org CASCADE;
CREATE TABLE neighbor_org
(
  id        BIGINT PRIMARY KEY NOT NULL,
  ext_id    VARCHAR(255),
  is_active BOOLEAN,
  name      VARCHAR(255)
);
DROP TABLE IF EXISTS neighbor_account CASCADE;
CREATE TABLE neighbor_account
(
  id               BIGINT PRIMARY KEY NOT NULL,
  account_number   VARCHAR(255),
  account_urn      VARCHAR(255),
  active           BOOLEAN,
  address_building VARCHAR(255),
  address_flat     VARCHAR(255),
  address_floor    VARCHAR(255),
  address_street   VARCHAR(255),
  created_on       TIMESTAMP,
  org_id           BIGINT,
  owner_phone      VARCHAR(255),
  updated_on       TIMESTAMP,
  CONSTRAINT fkbrfllq472jhlad8u3k3xj1qy3 FOREIGN KEY (org_id) REFERENCES neighbor_org (id)
);
DROP TABLE IF EXISTS neighbor_user CASCADE;
CREATE TABLE neighbor_user
(
  id                BIGINT PRIMARY KEY NOT NULL,
  account_id        BIGINT,
  activation_status VARCHAR(255),
  created_on        TIMESTAMP,
  login             VARCHAR(255),
  pin_code          VARCHAR(255),
  updated_on        TIMESTAMP,
  user_phone        VARCHAR(255),
  user_urn          VARCHAR(255),
  CONSTRAINT fkhe814v6e4r606qfbe82jk6iku FOREIGN KEY (account_id) REFERENCES neighbor_account (id)
);
DROP TABLE IF EXISTS neighbor_activation_token CASCADE;
CREATE TABLE neighbor_activation_token
(
  id           BIGINT PRIMARY KEY NOT NULL,
  created_on   TIMESTAMP,
  token        VARCHAR(255),
  token_status INTEGER,
  user_id      BIGINT,
  valid_to     TIMESTAMP,
  CONSTRAINT fkbudr9ds0db1imujxmsilykle6 FOREIGN KEY (user_id) REFERENCES neighbor_user (id)
);

DROP TABLE IF EXISTS neighbor_role CASCADE;
CREATE TABLE neighbor_role
(
  id        BIGINT PRIMARY KEY NOT NULL,
  user_role VARCHAR(255),
  user_id    BIGINT
);

DROP TABLE IF EXISTS neighbor_user_status_history CASCADE;
CREATE TABLE neighbor_user_status_history
(
  id                BIGINT PRIMARY KEY NOT NULL,
  activation_status VARCHAR(255),
  created_on        TIMESTAMP,
  remark            VARCHAR(255),
  user_id           BIGINT,
  CONSTRAINT fkr3pyt4kgm5en2x9mldrce7gq7 FOREIGN KEY (user_id) REFERENCES neighbor_user (id)
);

GRANT ALL PRIVILEGES ON ALL TABLES IN schema nb_server TO nb_server;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN schema nb_server TO nb_server;
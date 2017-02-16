DROP TABLE IF EXISTS neighbor_job_type CASCADE;
CREATE TABLE neighbor_job_type
(
  id            BIGINT PRIMARY KEY NOT NULL,
  org_ext_id    VARCHAR(255) NOT NULL,
  ext_id        VARCHAR(255) NOT NULL,
  name          VARCHAR(255) NOT NULL,
  is_enabled    BOOLEAN NOT NULL
);
--
DROP TABLE IF EXISTS neighbor_job_category CASCADE;
CREATE TABLE neighbor_job_category
(
  id            BIGINT PRIMARY KEY NOT NULL,
  ext_id        VARCHAR(255) NOT NULL,
  type_id       BIGINT NOT NULL,
  name          VARCHAR(255) NOT NULL,
  description   VARCHAR(255),
  is_enabled    BOOLEAN NOT NULL
);
ALTER TABLE neighbor_job_category ADD CONSTRAINT fk1_neighbor_job_category
    FOREIGN KEY (type_id) REFERENCES neighbor_job_type (id);
--
DROP TABLE IF EXISTS neighbor_job CASCADE;
CREATE TABLE neighbor_job
(
  id            BIGINT PRIMARY KEY NOT NULL,
  category_id   BIGINT NOT NULL,
  job_urn       VARCHAR(255) NOT NULL,
  name          VARCHAR(255) NOT NULL,
  description   VARCHAR(255),
  is_enabled    BOOLEAN NOT NULL
);
ALTER TABLE neighbor_job ADD CONSTRAINT fk1_neighbor_job
    FOREIGN KEY (category_id) REFERENCES neighbor_job_category (id);

--
GRANT ALL PRIVILEGES ON SCHEMA nb_server TO nb_server;
GRANT ALL PRIVILEGES ON ALL TABLES IN schema nb_server TO nb_server;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN schema nb_server TO nb_server;
DROP TABLE IF EXISTS neighbor_job_type CASCADE;
CREATE TABLE neighbor_job_type
(
  id            BIGINT PRIMARY KEY NOT NULL,
  org_ext_id    VARCHAR(255),
  ext_id        VARCHAR(255),
  name          VARCHAR(255),
  is_enabled    BOOLEAN
);
--
DROP TABLE IF EXISTS neighbor_job_category CASCADE;
CREATE TABLE neighbor_job_category
(
  id            BIGINT PRIMARY KEY NOT NULL,
  ext_id        VARCHAR(255),
  type_id       BIGINT NOT NULL,
  name          VARCHAR(255),
  description   VARCHAR(255),
  is_enabled    BOOLEAN
);
ALTER TABLE neighbor_job_category ADD CONSTRAINT fk1_neighbor_job_category
    FOREIGN KEY (type_id) REFERENCES neighbor_job_type (id);
--
DROP TABLE IF EXISTS neighbor_job CASCADE;
CREATE TABLE neighbor_job
(
  id            BIGINT PRIMARY KEY NOT NULL,
  category_id   BIGINT NOT NULL,
  job_urn       VARCHAR(255),
  name          VARCHAR(255),
  description   VARCHAR(255),
  is_enabled    BOOLEAN
);
ALTER TABLE neighbor_job ADD CONSTRAINT fk1_neighbor_job
    FOREIGN KEY (category_id) REFERENCES neighbor_job_category (id);


--
GRANT ALL PRIVILEGES ON SCHEMA nb_server TO nb_server;
GRANT ALL PRIVILEGES ON ALL TABLES IN schema nb_server TO nb_server;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN schema nb_server TO nb_server;
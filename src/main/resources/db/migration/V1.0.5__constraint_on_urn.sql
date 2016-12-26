ALTER TABLE neighbor_account
  ALTER COLUMN account_urn SET NOT NULL;

ALTER TABLE neighbor_account ADD CONSTRAINT urn_constraint UNIQUE (account_urn);

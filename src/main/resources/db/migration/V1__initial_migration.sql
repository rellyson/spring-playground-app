CREATE TABLE IF NOT EXISTS users (
  id         serial             PRIMARY KEY,
  name       VARCHAR (255)      NOT NULL,
  email      VARCHAR (255)      UNIQUE NOT NULL,
  created_at TIMESTAMP          NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP          NOT NULL DEFAULT NOW()
);

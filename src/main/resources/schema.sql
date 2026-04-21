CREATE TABLE IF NOT EXISTS users (
    id          UUID PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks (
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS time_records (
    id          UUID PRIMARY KEY,
    user_id     UUID         NOT NULL REFERENCES users(id),
    task_id     UUID         NOT NULL REFERENCES tasks(id),
    start_time  TIMESTAMP    NOT NULL,
    end_time    TIMESTAMP    NOT NULL,
    description TEXT
);


CREATE TABLE score(
    id int generated always as identity,
    game VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    points INTEGER NOT NULL,
    played_at TIMESTAMP NOT NULL
);




CREATE TABLE score(
    id int generated always as identity,
    game VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    points INTEGER NOT NULL,
    played_at TIMESTAMP NOT NULL
);

CREATE TABLE comments(
    id int generated always as identity,
    game VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    text VARCHAR(1000) NOT NULL,
    commented_at TIMESTAMP NOT NULL
);
CREATE TABLE rankings(
    id int generated always as identity,
    game VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    rating INT NOT NULL CHECK(rating >= 1 AND rating <=9),
    ranked_at TIMESTAMP NOT NULL,
    constraint uniq unique (game,username)
);
commit;
CREATE SEQUENCE IF NOT EXISTS books_book_id_seq;

CREATE TABLE IF NOT EXISTS books (
    book_id          numeric(9)  NOT NULL DEFAULT nextval('books_book_id_seq'),
    book_name        varchar(100) NOT NULL,
    author           varchar(100) NOT NULL,
    genre            varchar(50),
    user_id          integer      NOT NULL DEFAULT 1,
    google_volume_id varchar(100),
    rating           integer,
    status           varchar(20),
    CONSTRAINT books_pk PRIMARY KEY (book_id)
    );

CREATE UNIQUE INDEX IF NOT EXISTS uq_books_user_google
    ON books (user_id, google_volume_id);

CREATE TABLE IF NOT EXISTS genre (
    genre_name varchar(50) PRIMARY KEY
    );
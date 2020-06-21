INSERT INTO GENRE (GENRE) VALUES ('TEST first genre');
INSERT INTO GENRE (GENRE) VALUES ('TEST second genre');
INSERT INTO GENRE (GENRE) VALUES ('TEST third genre');
INSERT INTO GENRE (GENRE) VALUES ('TEST forth genre');

INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'TEST First book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (2, 'TEST Second book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'TEST Third book title');

INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('TEST FirstBookAuthorSurname', 'TEST FirstBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('TEST FirstBookSecondAuthorSurname', 'TEST FirstBookSecondAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('TEST SecondBookAuthorSurname', 'TEST SecondBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('TEST ThirdBookAuthorSurname', 'TEST ThirdBookAuthorName');

INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 1);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 2);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (2, 3);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (3, 4);

INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('TEST Name', 'TEST Very interesting book', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('TEST Name2', 'TEST Good quality of material', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('TEST Name3', 'TEST Recommend this book', 2);

COMMIT;
INSERT INTO GENRE (GENRE) VALUES ('first genre');
INSERT INTO GENRE (GENRE) VALUES ('second genre');
INSERT INTO GENRE (GENRE) VALUES ('third genre');
INSERT INTO GENRE (GENRE) VALUES ('forth genre');

INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'First book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (2, 'Second book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'Third book title');

INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('FirstBookAuthorSurname', 'FirstBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('FirstBookSecondAuthorSurname', 'FirstBookSecondAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('SecondBookAuthorSurname', 'SecondBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('ThirdBookAuthorSurname', 'ThirdBookAuthorName');

INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 1);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 2);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (2, 3);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (3, 4);

INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('Name', 'Very interesting book', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('Name2', 'Good quality of material', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('Name3', 'Recommend this book', 2);

COMMIT;
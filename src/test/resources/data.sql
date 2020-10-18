INSERT INTO GENRE (GENRE) VALUES ('test first genre');
INSERT INTO GENRE (GENRE) VALUES ('test second genre');
INSERT INTO GENRE (GENRE) VALUES ('test third genre');
INSERT INTO GENRE (GENRE) VALUES ('test forth genre');

INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'test First book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (2, 'test Second book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'test Third book title');

INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('test FirstBookAuthorSurname', 'FirstBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('test FirstBookSecondAuthorSurname', 'FirstBookSecondAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('test SecondBookAuthorSurname', 'SecondBookAuthorName');
INSERT INTO AUTHOR (SURNAME, NAME) VALUES ('test ThirdBookAuthorSurname', 'ThirdBookAuthorName');

INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 1);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (1, 2);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (2, 3);
INSERT INTO BOOK_AUTHOR (ID_BOOK, ID_AUTHOR) VALUES (3, 4);

INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('test Name', 'Very interesting book', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('test Name2', 'Good quality of material', 1);
INSERT INTO COMMENT (NAME, SUBJECT, ID_BOOK) VALUES ('test Name3', 'Recommend this book', 2);

COMMIT;
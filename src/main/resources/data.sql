INSERT INTO GENRE (GENRE) VALUES ('first genre');
INSERT INTO GENRE (GENRE) VALUES ('second genre');
INSERT INTO GENRE (GENRE) VALUES ('Third genre');
INSERT INTO GENRE (GENRE) VALUES ('4');

INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'First book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (2, 'Second book title');
INSERT INTO BOOK (ID_GENRE, TITLE) VALUES (1, 'Third');

INSERT INTO AUTHOR (ID_BOOK, SURNAME, NAME) VALUES (1, 'FirstBookAuthorSurname', 'FirstBookAuthorName');
INSERT INTO AUTHOR (ID_BOOK, SURNAME, NAME) VALUES (1, 'FirstBookSecondAuthorSurname', 'FirstBookSecondAuthorName');
INSERT INTO AUTHOR (ID_BOOK, SURNAME, NAME) VALUES (2, 'SecondBookAuthorSurname', 'SecondBookAuthorName');
INSERT INTO AUTHOR (ID_BOOK, SURNAME, NAME) VALUES (3, 'ThirdBookAuthorSurname', 'ThirdBookAuthorName');

commit;
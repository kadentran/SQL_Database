INSERT INTO item VALUES (1,'Shelf 1','C How to Program','Learn how to program in C language','Available','1');

INSERT INTO item VALUES (2,'Shelf 1','C How to Program','Learn how to program in C language','Available','1');
INSERT INTO item VALUES (3,'Shelf 2','e-Business and eCommerce How to Program','Learn how to do e-Business and eCommerce','Available','1');
INSERT INTO item VALUES (4,'Shelf 2','Internet and World Wide Web How to Program','Learn how to access Internet and World Wide Web','Available','1');
INSERT INTO item VALUES (5,'Shelf 5','The Complete Java 2 Training Course','Learn how to program in Java language','Available','1');
INSERT INTO item VALUES (6,'Shelf 8','Maroon 5 Music CD','Pop- Maroon 5','Available','2');
INSERT INTO item VALUES (7,'Shelf 9','Popular Mechanics','December 2020','Available','3');
Select * from item;

INSERT INTO itemtype VALUES (1,'Book');
INSERT INTO itemtype VALUES (2,'CD/DVD');
INSERT INTO itemtype VALUES (3,'Magazine');
Select * from itemtype;

INSERT INTO booktype VALUES(1,'Paperbook');
INSERT INTO booktype VALUES(2,'ebook');
INSERT INTO booktype VALUES(3,'Audiobook');
Select * from booktype;

INSERT INTO booksection VALUES(252,'Science',1);
INSERT INTO booksection VALUES(352,'Technical',1);
INSERT INTO booksection VALUES(452,'Business',1);
Select * from booksection;

INSERT INTO publisher VALUES(1,'HarperCollins');
INSERT INTO publisher VALUES(2,'McGraw-Hill Education');
INSERT INTO publisher VALUES(3,'Pearson');
Select * from publisher;

INSERT INTO book VALUES (0130895725,1,3,02/28/2021,1,5876487,3434564,1235,1,252,1,2);
INSERT INTO book VALUES (0140896723,1,7,03/8/2020,2,4872347,9856321,998,2,352,2,4);
INSERT INTO book VALUES (0248765727,2,4,01/5/1998,3,8732534,2316619,1438,3,452,3,1);
INSERT INTO book VALUES (0768766572,2,1,01/5/1978,1,6545654,7624453,678,1,252,4,3);
INSERT INTO book VALUES (0856465386,1,5,01/5/1992,2,7444342,3742424,1118,1,352,5,2);
Select * from book;

INSERT INTO author VALUES (1,'Harvey','Smith');
INSERT INTO author VALUES (2,'James','Doe');
Select * from author;
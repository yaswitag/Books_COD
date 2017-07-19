
connect system/password

create user books identified by books;

grant connect, resource to books;

connect books/books

create table subjects 
( subcode number(3) primary key,
  subname varchar2(50) 
);

insert into subjects values(1,'Java');
insert into subjects values(2,'Oracle');
insert into subjects values(3,'ASP.NET');
insert into subjects values(4,'Android');


create table books
( bookid    number(5)  primary key,
  title     varchar2(100),
  author    varchar2(100),
  publisher varchar2(50),
  price     number(6,2),
  discount  number(5,2),
  subcode   number(3) references subjects(subcode)
);

insert into books values(1,'Java Comp. Ref.','Herbert Schildt','TM',550,20,1);
insert into books values(2,'JSF 2.0 Comp. Ref.','Ed Burns, Chris Schalk','McGraw Hill',575,30,1);

insert into books values(3,'Oracle Database 11g SQL','Jason Price','Oracle Press',450,10,2);
insert into books values(4,'Oracle Comp. Ref.','Kevin Loney','Oracle Press',675,20,2);


insert into books values(5,'Asp.net 4.0 unleashed','Stephen Walther','Pearson',875,25,3);
insert into books values(6,'Pro. Asp.net in C# 2010','MacDonald','Apres',650,20,3);

insert into books values(7,'Beginning Android Application Development','Wei-Meng Lee','Willey',650,30,4);
insert into books values(8,'Android Application Development','Blake Meike, Rick Rogers, Zigurd Mednieks, Lombardo John','O''Reilly',450,10,4);


create table orders
( orderid   number(5) primary key,
  customer  varchar2(50),
  address   varchar2(100),
  email     varchar2(50),
  mobile    varchar2(10),
  total     number(7,2),
  status    char(1)
);

create sequence orderid_seq start with 1 nocache;
 
create table orderitems
( orderid   number(5)  references orders(orderid),
  bookid    number(5)  references books(bookid),
  qty       number(2),
  price     number(6,2),
  discount  number(5,2),
  primary key(orderid,bookid)
);



  
  

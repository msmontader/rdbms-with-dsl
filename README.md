### What is this repository? ###
 
 * Simple Database System with a DSL (this was a class project for CS61B at UC Berkeley. Spring 2017).
 
 * A small version of what is called a relational database management system (DBMS).
 	This project has a DSL (Domain Specific Language), where a user can interact with the system.
 	The project is similar to the declarative programming language SQL.
 	Although this is considered a class project, the staff provided a very minimal code (only the string parser for the DSL input).

* Version 1.0
 
 ### How do I get set up? ###
After you download the source code, run the file `Main` and you will be able to interact with the system using the commandline console.
 
 ### Commands: ###
`create table <table name> (<column0 name> <type0>, <column1 name> <type1>, ...)`
Create a table with the given name. The names and types of the columns of the new table are supplied in a parenthesized list, in order. 
This syntax defines the column order for this table.
`create table <table name> (<column0 name> <type0>, <column1 name> <type1>, ... )`
 
`Create table <table name> as <select clause>`
* Create a table with the given name. The names and types of the columns of the new table are supplied in a parenthesized list, in order. 
	This syntax defines the column order for this table.
	EX: `create table students (name string, gpa float)`
 
Create a table with the given name. The columns, content, and types of columns of the table are those of the intermediate table created by the result of executing the select clause.
It is an error to create a table with no columns, and it is also an error to generate a table that already exists.
 
Load
`load <table name>`
* `Create table <table name> as <select clause>`
 
Store
`store <table name>`
	Create a table with the given name. The columns, content, and types of columns of the table are those of the intermediate table created by the result of executing the select clause.
	It is an error to create a table with no columns, and it is also an error to generate a table that already exists.
 
Write the contents of a database table to the file <table name>.tbl.
If the TBL file already exists, it should be overwritten.
* Load
	`load <table name>`
	
	EX: load students 
 
Drop Table
`drop table <table name>`
* Store
	`store <table name>`
	EX: store students
 
Delete the table from the database
	Write the contents of a database table to the file `<table name>.tbl`
	
	If the TBL file already exists, it should be overwritten.
 
Insert Into
`insert into <table name> values <literal0>,<literal1>,...`
* Drop Table
	`drop table <table name>`
	
	EX: drop students
 
Insert the given row (the list of literals) to the named table. 
The table must already be in the DB, and the provided values must match the columns of that table. 
If a provided value cannot be parsed into the type of the column it is listed in; it is an error. 
The given row is appended to the table, becoming the last row in its row order.
	Delete the table from the database
 
Print
`print <table name>`
* Insert Into
	`insert into <table name> values <literal0>,<literal1>,...`
	
	EX: insert into students values John, 3.6
 
Select
Select statements are used to extract data from the database in a programmatic fashion. 
Instead of simply writing to and printing individual tables, select statements allow you to form more complicated requests. 
They take the form below:
	
	Insert the given row (the list of literals) to the named table. 
	The table must already be in the DB, and the provided values must match the columns of that table. 
	If a provided value cannot be parsed into the type of the column it is listed in; it is an error. 
	The given row is appended to the table, becoming the last row in its row order.
 
select <column expr0>,<column expr1>,... from <table0>,<table1>,... where <cond0> and <cond1> and ...
* Print
	`print <table name>`
	EX: print students
 
`select <column expr> from <table0>`
 
The order of the columns in the new table is defined by the order they are listed in the select.
In the case that all columns are selected (with the * operator), the order is defined by the column order for the join.
* Select
	Select statements are used to extract data from the database in a programmatic fashion. 
	Instead of simply writing to and printing individual tables, select statements allow you to form more complicated requests. 
	They take the form below:

	select <column expr0>,<column expr1>,... from <table0>,<table1>,... where <cond0> and <cond1> and ...

	
	`select <column expr> from <table0>`

	The order of the columns in the new table is defined by the order they are listed in the select.
	In the case that all columns are selected (with the * operator), the order is defined by the column order for the join.
 
 ### Who do I talk to? ###
 
 Muntadher Inaya
 Email: mr.monty@berkeley.edu
 Phone: 619-621-1457
 
 ### Code rights and code of conduct ###
 
 The specifications and guidelines for this project was set by the University of California, Berkeley CS61B staff.
 If the public visibility of this project is violating the course policy, please contact me by email or phone to make it
 private.
 
©copy Muntadher Inaya All rights reserved.

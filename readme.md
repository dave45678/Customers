



<div id="content-wrapper" class="ic-Layout-contentWrapper">
<div id="content" class="ic-Layout-contentMain" role="main">
<div id="assignment_show" class="assignment content_underline_links">
<div class="description user_content student-version enhanced">
<p>This assignment has a lot of steps. But I'll walk you through them. And after that you will be an SQL-Database Ninja.</p>
<p>Do you know what the most common database in business is? Think Excel. It seems every computer has a copy. It seems everybody uses it. Most people use it for the wrong stuff. Despite that it often works. In business, doing what works is more important than doing what's best.</p>
<p>In this exercise, you will be importing the data. Then you will follow a process that known as<span>&nbsp;</span><strong>Normalizing the Database</strong>. There are a million ways to normalize your database. You're going to import a spreadsheet full of data. Then divide it into different tables. This is a realistic scenario. It plays out everyday throughout corporate America. Clients send you data. The data is a mess. You need that data in a database.</p>
<h4>Here are the steps we will follow</h4>
<ol>
<li>Download the&nbsp;<a class="instructure_file_link instructure_scribd_file" title="customer.csv" href="https://canvas.instructure.com/courses/1630629/files/78373793/download?wrap=1" data-api-endpoint="https://canvas.instructure.com/api/v1/courses/1630629/files/78373793" data-api-returntype="File">customer.csv</a>&nbsp;file<span class="instructure_file_holder link_holder"></span></li>
<li>Import the customers file into Workbench.Hint: Right-click on the database name and select table import option from the menu.</li>
<li>Write a query to find the number of unique companies in the table.</li>
<li>Create a new table for companies and move that data to the new table. Only move the unique companies. Then add a column to your customers table and update the the new column which is called CompanyID.</li>
<li>Create a new table for cities and a new table for states and positions. Move that data to the new tables in the same way you did above. Move unique cities and unique states to the two new tables.</li>
<li>Delete those columns from this table and add keys to link to the new tables.</li>
<li>Create a query that recreates the original data set&nbsp;using joined tables.</li>
<li>Export the data as SQL import statements (optional).</li>
</ol>
<p>Submit your sql script but you don't have to submit the export file in #9.</p>
<p>Some help for you......</p>
<div>
<div>you want to move all the unique company names to another table&nbsp;</div>
<div>and then delete them from the customer table.</div>
<div></div>

```sql
drop database customers; --only run this if you're starting over<br />
create database customers;
-- switch to the customers database (It's case sensitive)
use customers;

-- import customers.csv into a table called Customer (case-sensitive)
-- Right-click on tables and select import, use the wizard to import the csv file
-- see how many customers are in our table: (use back ticks for strings)
select count(*) as `Customer Count` from customer;

-- how many unique companies are in our table?
select count(distinct company)as 'Distinct Companies' from customer;

-- add an Id to the customer table
alter table customer add customerId int not null primary key auto_increment;

-- add a column for the CompanyID to the customers table
alter table customer add companyID int;

-- notice that the companyId is null
select companyID, company from customer;

-- create a table for the companies
-- this statement will also create a companyID column which will
-- increment when you insert a new record
create table company (
companyID int NOT NULL AUTO_INCREMENT,
company varchar(255),
primary key (companyID)
);

-- see what's in your company table now
select * from company;

-- generate a sql statement which shows which companies will be added to the new customer table
select distinct company from customer where length(company)&gt;0 and company is not null  order by company;

-- add the above companies from customers to the company table
insert into company (company) select distinct company from customer where length(company)&gt;0 and company is not null  order by company;

-- look at what you've done
select * from company;

-- another way to select is to list the fields
select companyID, company from company;

/*
If you get ...
Error Code: 1175. You are using safe update mode and you tried 
to update a table without a WHERE that uses a KEY column 
To disable safe mode, toggle the option in 
Preferences -&gt; SQL Editor and reconnect.

To reconnect: Query Menu -&gt; Reconnect to Server
*/

-- update the companyId in the customers table
update customer c set c.companyID = (select t.companyID from
company t where t.company=c.company);

-- query to check your data
select c.companyID,c.company,t.companyID,t.Company from
customer c inner join company t on c.companyID=t.companyID;

-- remove the company column from the customers table. It is no longer needed
alter table customer drop column company;

-- also remove fullname, we don't need calculated columns. They're a maintenance headache
alter table customer drop column fullname;

-- You can generate fullname more efficiently as:
select CONCAT(`FirstName`,' ',`LastName`) as `Full Name` from customer;

-- notice you won't see the company (or fullname) column
select * from customer;

-- the company column and the id are in Company
select * from company;

-- a query to bring it all back together
select CONCAT(`FirstName`,' ',`LastName`) as `Full Name`, company from customer 
inner join company on 
customer.companyid=company.companyid;
```

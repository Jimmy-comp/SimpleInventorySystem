# SimpleInventorySystem

A Spring Boot Java Application

Please check whether you have followed the guideline.

First, you can generate the Spring Boot project from Spring initializr [https://start.spring.io](https://start.spring.io).\

## My configuration:

Project: `Maven Project`\
Language: `Java`\
Spring Boot: `2.5.2`\
Project Metadata: Input your own project name, Choose `Jar` as Packaging and `Java v11` for the version.

Then, you can import the project in IntelliJ IDEA (CE).\
Run the `WebsiteApplication.java` class file.\

The application will first check whether the database has data or not.\
If no, the application will add the Default Datasets to the database.\
(You can configure the datasets in `run` function in `WebsiteApplication.java`).\

The Inventory should maintains following attribute:\
+ `Name`
+ `Product Code`
+ `Quantity`
+ `Location`


Open [http://localhost:8080](http://localhost:8080) to view it in the browser.

Now, you can test the function by clicking different button.

### List Products

You can see all products by clicking the `List Products` button.\
The products will be shown in the table on the right.

### Show Inventory Level of Given Product Code

You can see specific products by searching the Product Code.\
The results will be shown in the table on the right.

### Transfer Inventory

You can transfer products by clicking the `Move` button.\
A form will pop up below the table,\
Input the transfer quantities and destination,\
The records will changed after clicking the `Move` button.\
(Please change or add the location in the `<option>` tag manually)

### Add Inventory

You can add inventory by clicking the `Add Product` button.\
You will go to other page which has a form for you to add inventory.

### Save Inventory as CSV File

You can save inventory data as CSV files by clicking the button.\
One saves all location of such product, other one saves all records.

### MongoDB

You can store data in MongoDB.\
Add the denpendencies in `pom.xml`.\
Then, change the MongoDB URL in `application.properties`.\
I have already put my MongoDB URL in that.

## The working progress - Day 1

I have create the web application and create the classes for creating data objects and the services, such as add inventory, update inventory and find specific inventory.\
I also connect the database and the application, then I can store the data.

## The working progress - Day 2

I have create the layout of the system's UI. I applied Ajax in the website.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-developing-web-applications)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

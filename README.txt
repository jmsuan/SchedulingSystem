===============================================
= SchedulingSystem - An Advanced Java Project =
===============================================

This is a scheduling application built in Java which connects to a database for data and storage. The application
utilizes advanced Java concepts, lambdas for example, to enhance maintainability and legibility. Javadocs that detail
all the packages and classes that help accomplish this are provided.

The application can display appointments, customers, and reports related to those appointments. Users can interact with
the various controls through a GUI, and can update or delete records from the database.


Author Information:
===================
Author Name: Jonathan Lane
Inquiry Email: jonathan@lane.red
Version: 1.0
Date: 2023-10-31


Software & Drivers Used:
========================
- IntelliJ IDEA 2023.2.2 (Community Edition)
- Java SE 17.0.1 (LTS)
- JavaFX SDK 17.0.6 (LTS)
- MySQL Java Connector Driver (mysql-connector-j-8.2.0)

Note of the MySQL driver: This is the latest version of the driver, they renamed/refactored it from "java" to "j".
                          The previous versions that were suggested have CVEs and wouldn't expert properly, so I
                          updated it. Info confirming the change is at the link below.
                          https://dev.mysql.com/doc/connectors/en/connector-j-installing-maven.html

How To Run the Program:
=======================
Before launching the software, you may want to install a MySQL database server somewhere you can access. This software
assumes that you have the full-kitted database that adheres to a specific ERD, with some additional constraints set.

Step 1. Configure your database server's address and credentials within the JDBC class under
lanej.schedulingsystem.dao.JDBC. The default parameters are as follows:
    Server:   localhost
    Port:     3306
    Database: client_schedule
    Username: sqlUser
    Password: Passw0rd!

Step 2. Compile and launch the software. You will be greeted with a login screen, which authenticates you based on User
records stored in the connected database. If you are having trouble, try checking the terminal output to make sure you
have successfully connected to the database.

Step 3. Once you are logged in, you will be greeted with two tables. One lists all the Customers you have in your
database, and the other lists all the appointments you have in your database. You may add, modify, or delete records
of either one by clicking on the respective buttons at the bottom of each table.

    Note: If you have an appointment within 15 minutes of the time you log in, you will be notified with the details of
          the appointment. You will be notified each time you enter the main screen (the one with both tables) until
          the appointment has passed.

Step 4. If you would like to see any reports that aggregate information from data in your customer/appointment data
set, you may do so by clicking on the button at the bottom of the main screen, labelled "See Reports."

Step 5. Once you're finished with using the application, you can exit by either logging out using the button at the
bottom of the main screen, then closing the window, or by simply closing the window.


Reading the Reports:
====================
The software creates three different reports for you to view by clicking the "See Reports" button. To navigate between
the various reports, click on the tab of the report you want to see (at the top of the window).

Reports that are available include:
- The total number of appointments by type and month.
- A schedule of appointments by "Contact," listing only appointments they are associated with.
- The total number of customers that have (or don't have) an appointment.

Expanding on the third report:
The third report (listed above) fetches and displays the total number of customers from the database, and identifies
how many of them have scheduled appointments. This is done by iterating over all appointments and storing the customers
who appear into a HashSet (ensuring no duplicates), then finds the count of them. This third report can be very useful
for measuring the active engagement of your customers, which can make determining the success of marketing campaigns a
breeze. By also being able to list how many customers *don't* have appointments, you can measure your potential
opportunities for engaging leads as well.
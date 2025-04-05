# CSP BankAssist

## Overview
CSP BankAssist is a web-based banking system designed for Customer Service Point (CSP) branches to provide essential banking services efficiently. This application reduces the dependency on main bank branches by enabling CSP operators to process transactions quickly and securely.

## Objectives
- To streamline small-scale banking operations at CSP centers.
- To provide a user-friendly interface for CSP operators to handle transactions.
- To reduce the need for customers to visit main bank branches for basic services.
- To enhance security and efficiency in CSP banking transactions.

## Key Features
1. **User Authentication**  
   - Secure login system for CSP operators.  
   - Session management for active users.

2. **Balance Inquiry**  
   - Allows operators to check customer account balances instantly.  

3. **Deposits & Withdrawals**  
   - CSP operators can facilitate deposit and withdrawal transactions.  

4. **Fast Cash Transactions**  
   - Quick withdrawal of predefined cash amounts for faster service.  

5. **PIN Change**  
   - Allows customers to securely update their ATM PINs via CSP operators.  

6. **Transaction Logging**  
   - Records all transactions in the DB for audit and security purposes.  

## Technologies Used
- **Frontend:** HTML, CSS  
- **Backend:** Java (Servlets, JSP)  
- **Database:** Oracle MySQL  
- **Server:** Apache Tomcat  

## Project Structure
- **`src/main/java/com/csp/`** → Contains Java Servlet files for handling business logic.  
- **`src/main/webapp/`** → Frontend files (HTML, CSS, JSP) for user interaction.  
- **`WEB-INF/`** → Web application configuration files.  

## Deployment Guide  
### **Prerequisites**  
- Java Development Kit (JDK)  
- Apache Tomcat Server (Version 9.0 or above)  
- MySQL Database or Supabase  
- Eclipse (for development)  

### **Setup Steps**  
1. **Clone the Repository**  
   - Use `git clone https://github.com/itsgowtham14/CSP-BankAssist.git`  
   - Open the project in your preferred IDE (e.g., Eclipse with Dynamic Web Module support).

2. **Configure Database**  
   - Set up a MySQL database named `csp_management`.  
   - Import the SQL dump file (e.g., `csp_management.sql`) using phpMyAdmin or MySQL Workbench.  
   - Update your database connection code (like in `DBConnection.java`) with the appropriate DB URL, username, and password.

3. **Deploy on Tomcat**  
   - Right-click the project in Eclipse → `Run on Server`, or  
   - Export the project as a `.war` file and place it in the `webapps/` folder of your Tomcat installation.  
   - Start Tomcat Server and navigate to `http://localhost:8080/CSP_BankAssist/`.

4. **Login and Use**  
   - Use valid test credentials to log in and begin using the application.

## Future Enhancements
- **Mobile-friendly design** for better usability.  
- **Transaction reports** for CSP operators.  
- **Integration with OTPs for pin changing** for better  security.  
- **Multi-factor authentication** for enhanced security.  

## Author
**Gowtham - 231FA04094**, **Harshitha - 231FA04097**, **Deepak - 231FA04322**, **Bhavitha - 231FA04408**  
B.Tech CSE, VFSTR University  

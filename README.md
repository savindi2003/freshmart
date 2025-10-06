# 🛒 FreshMart - POS System (Java SE)

[![Platform](https://img.shields.io/badge/platform-Desktop-blue)]()
[![Language](https://img.shields.io/badge/language-Java-orange)]()
[![Database](https://img.shields.io/badge/database-MySQL-green)]()
[![Reporting](https://img.shields.io/badge/reports-JasperReports-purple)]()
[![License](https://img.shields.io/badge/license-MIT-blue)]()

**FreshMart** POS (Point of Sale) is a robust Java SE-based application designed to manage the operations of the FreshMart supermarket chain efficiently and seamlessly.

This modern desktop system was developed to replace manual, error-prone record-keeping, which was unsuitable for the supermarket's growth. It is specifically engineered to streamline key retail functions, including employee management, supplier coordination, product inventory, and financial operations

----
## ✨ Features
The FreshMart POS system provides comprehensive functionalities across multiple core supermarket operations:

- **Employee Management**
    - Role-based sign-in for **Admin , Cashier , Store Manager** and **Weighing Station Operator**  dashboards.
    - Manage employee records (Add, Update, Delete, Search) and assign permissions.
  
- **Inventory & Stock Management**
    - Manage product lists, categories, and brands.
    - Track Goods Received Notes (GRN) for new stock additions.
    - Real-time stock updates and reporting for 
    - Low Stock and Out of Stock products.

- **Sales & Invoicing**
    - Generate quick and error-free invoices with options for payment tracking.
    - Manage sales history and generate comprehensive reports.
    - Handle Weighting and Labeling for products sold by weight.

- **Supplier Coordination**
    - Maintain records for suppliers and their respective companies.
    - Supplier Performance Reports based on GRN history.
      
- **Return Management**
    - Structured handling of refunds and returns for damaged products and customer returns.

- **Customer & Profit**
    - Manage customer data, essential for loyalty programs and personalized services.
    - Track and calculate Profit by managing income and expenses.

---

## ⚠️ Exception Handling & Logging

FreshMart implements **exception handling** throughout the application to ensure smooth operation.  

- All runtime errors, SQL exceptions, and unexpected events are **caught and logged**.  
- Logs are saved in a **log file** (`log.txt`) for debugging and maintenance.  
- This helps in monitoring system behavior, troubleshooting issues, and maintaining data integrity.  

---

## 🛠️ Tech Stack

| Component | Technology |
|------------|-------------|
| Language | Java SE |
| Database | MySQL (`freshmart_db`) |
| Reporting Tool | JasperReports |
| IDE | NetBeans |
| UI Framework | Java Swing |

---

## ⚙️ Requirements

- ✅ **JDK 17** or higher  
- ✅ **MySQL Server**  
- ✅ **NetBeans IDE** (or IntelliJ IDEA)  
- ✅ **JasperReports** library configured  
- ✅ Database created (use provided `.sql` file)

---

## 🚀 Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/FreshMart.git
   
2. Create the MySQL database
3. Import `freshmart.sql` file
4. Update database connection in Java code:

   ````bash
    String url = "jdbc:mysql://localhost:3306/freshmart_db";
    String user = "root";
    String password = "yourpassword";
   ````
5. Build and run the application

---

## 📊 Reports (JasperReports)
FreshMart uses JasperReports for:

- Printable invoices for customers
- GRN Reports
- Product Barcode
- GRN Summary Report
- Products Return Statement
- Expenses Report
- Daily Sales Report
- Loss/Profit Statement
- Low Stock Product Report
- Out of Stock Product Report

---

## 👩‍💻 Author
**Savindi Duleesha**  
📧 savindiduleesha@gmail.com  
🌐 [Portfolio](https://savindi2003.github.io/my-portfolio/)

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.





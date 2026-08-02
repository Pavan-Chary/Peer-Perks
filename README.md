# Peer Perk Ledger 

Peer Perk Ledger is a secure, monolithic backend application built with Spring Boot 3. It serves as an employee recognition and peer-to-peer reward system. Users can log in via Google SSO, transfer monthly allowance points to peers as a "thank you," and redeem earned points for physical or digital rewards.

---

##  Features

* **Google OAuth2 Authentication:** Secure, passwordless login using Google Single Sign-On (SSO).

* **Role-Based Access Control (RBAC):** Dynamic role assignment distinguishing standard users from Admins (who handle perk redemptions).

* **Digital Wallet & Transaction Log:** A reliable wallet system separating:

    * **Allowance** (points you can give)
    * **Redeemable** (points you can spend)
      Backed by an immutable transaction history.

* **Advanced Transaction Filtering:** Dynamic SQL querying using Spring Data JPA Specifications to filter transactions by type, date range, and amount.

* **Automated Monthly Resets:** A fault-tolerant, paginated Cron job (`@Scheduled`, `@Retryable`) that safely resets user allowances on the 1st of every month without overwhelming JVM memory.

* **Pull-Based Notification System:** A highly optimized notification inbox featuring bulk-update operations to reduce frontend API calls.

---

##  Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3 (Web, Security, Data JPA)
* **Database:** MySQL
* **Security:** Spring Security (OAuth2 Client)

---

## ️ Local Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/peer-perk-ledger.git
cd peer-perk-ledger
```

---

### 2. Configure Environment Variables in your IDE

This application relies on environment variables for security. Instead of hardcoding passwords, add the following variables to your IDE's Run/Debug Configurations (e.g., IntelliJ IDEA → Edit Configurations → Environment Variables):

```env
DATABASE_PASSWORD=your_mysql_password
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
ADMIN_EMAILS=admin@example.com,receptionist@example.com
```

---

### 3. Database Setup

Ensure you have a local MySQL instance running with a database named:

```
peer_perks
```

Hibernate will automatically generate the schema on startup.

---

### 4. Run the Application

```bash
mvn spring-boot:run
```

The server will start on:

```
http://localhost:8080
```

---

##  Core API Endpoints

```
GET    /api/v1/users/me                - Get current logged-in user profile
POST   /api/v1/ledger/transfer        - Transfer points to a peer
POST   /api/v1/ledger/redeem          - (Admin only) Redeem points
GET    /api/v1/transactions           - Fetch paginated & filtered transactions
GET    /api/v1/notifications          - Fetch unread notifications
POST   /api/v1/notifications/read-all - Mark all notifications as read
```

---

##  Future Architecture Plans (V2)

This application is currently designed as a highly cohesive **Monolith**.

Future iterations will extract the Notification layer into an independent Microservice communicating via **Apache Kafka**.

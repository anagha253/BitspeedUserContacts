# 📇 Bitspeed User Contacts API

Bitspeed User Contacts is a Spring Boot-based API that identifies and links user contacts using their email and/or phone number. It helps in de-duplicating user data and maintaining a single source of truth for contact information.

---

## 🚀 Live Deployment

The API is live and accessible at:

🌐 **[https://bitspeed-app-latest.onrender.com](https://bitspeed-app-latest.onrender.com)**

---

## 📌 API Endpoint

### 🔍 Identify Contact

**Endpoint:** `POST /api/identify`

Identify a user based on the provided `email` and/or `phoneNumber`. The service links related contacts under one `primaryContactId` and tracks duplicates as secondary contacts.

#### ✅ Request Body

```json
{
  "email": "john.doe@example.com",
  "phoneNumber": "9876543210"
}
```

#### 🔁 Sample Response

```json
{
  "contact": {
    "primaryContactId": 1,
    "emails": ["john.doe@example.com"],
    "phoneNumbers": ["9876543210"],
    "secondaryContactIds": [2, 3]
  }
}
```

#### 🧪 cURL Example

```bash
curl --location 'https://bitspeed-app-latest.onrender.com/api/identify' --header 'Content-Type: application/json' --data-raw '{
  "email": "john.doe@example.com",
  "phoneNumber": "9876543210"
}'
```


Actual example:


<img width="643" alt="image" src="https://github.com/user-attachments/assets/ae037177-d623-455f-a3d0-3dca5634a7af" />


---

## 🛠️ Project Setup

### 1️⃣ Prerequisites

- Java 17
- Maven 3.x
- Docker
- PostgreSQL
- Git

### 2️⃣ Clone & Build Locally

```bash
git clone https://github.com/anagha253/BitspeedUserContacts.git
cd bitspeedUserContacts
```

### 3️⃣ Build JAR

```bash
mvn clean package -DskipTests
```

> The generated `.jar` file will be located in the `target/` directory.

---

## 🐳 Dockerized Deployment

### 🏗️ Build Docker Image

```bash
docker buildx build --platform=linux/amd64 -t anagha253/bitspeed-app:latest .
```

> Render requires `linux/amd64` platform.

### 🚀 Run Locally

```bash
docker run -p 8080:8080   -e DB_URL=jdbc:postgresql://<host>:5432/<database>   -e DB_USER=<user>   -e DB_PASSWORD=<password>   anagha253/bitspeed-app:latest
```

---

## ☁️ Deploying on Render

### 🔧 Create Web Service

1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Create a new Web Service
3. Choose **"Deploy an image from Docker registry"**
4. Use DockerHub image URL:
   ```
   anagha253/bitspeed-app:latest
   ```
5. Set the environment to:
   ```
   Platform: Linux/AMD64
   Environment: Docker
   ```

### 🌱 Add Environment Variables

| Key          | Value                                  |
|--------------|----------------------------------------|
| DB_URL       | `jdbc:postgresql://<host>:5432/<db>`   |
| DB_USER      | `<your_db_user>`                       |
| DB_PASSWORD  | `<your_db_password>`                   |

---

## 🧪 Test the Live API

After deployment, test it using:

```bash
curl --location 'https://bitspeed-app-latest.onrender.com/api/identify' --header 'Content-Type: application/json' --data-raw '{
  "email": "mohan3@gmail.com",
  "phoneNumber": "8999999922"
}'
```

---

## 🧰 Tech Stack

- ⚙️ Java 17
- 🚀 Spring Boot 3.x
- 🐘 PostgreSQL
- 🐳 Docker
- ☁️ Hosted on Render
- 🔗 JPA + Hibernate

---

## 🔒 Environment Setup (.env)

If running locally, set these in `application.properties` or `.env`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=youruser
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## 🧹 Project Structure

```
src/
├── main/
│   ├── java/com/app/bitspeed/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   ├── repository/
│   │   └── BitspeedApplication.java
│   └── resources/
│       ├── application.properties
│       └── schema.sql
```

---

## 📄 License

MIT License – feel free to fork, use, or modify.

---

## 🙋‍♀️ Author

**Anagha Mohan**  
Backend Developer | Passionate about Clean APIs and Microservices  
🌐 GitHub: [@anagha253](https://github.com/anagha253)

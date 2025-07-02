# 📇 Bitspeed User Contacts API

Bitspeed User Contacts is a Spring Boot-based API that identifies and links user contacts using their email and/or phone number. It helps in de-duplicating user data and creating a single source of truth for contact information.

---

## 🚀 Live API

The API is deployed and live at: https://bitspeed-app-latest.onrender.com/<link>

---

## 📌 API Endpoint

### Identify Contact

**POST** `/api/identify`

Identify a user based on the provided email and/or phone number. It checks existing records and links them appropriately under a common `primaryContactId`.

#### ✅ Request Body

```json
{
  "email": "xyz@gmail.com",
  "phoneNumber": "0000"
}
```

Example:
<img width="627" alt="image" src="https://github.com/user-attachments/assets/9235badb-d860-4aa1-961d-67d0159a5d00" />


####⚙️ Tech Stack
-Java 17
-Spring Boot 3.x
-PostgreSQL (via JDBC)
-Docker & DockerHub
-Hosted on Render

The Docker image is linux-arm64 type
``` docker buildx build --platform=linux/amd64 -t anagha253/bitspeed-app:latest .```






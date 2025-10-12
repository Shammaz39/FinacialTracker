## 🧾 **FinTrack API Endpoints Summary**

---

### 🟢 **AUTH CONTROLLER**

**Base URL:** `/api`

#### 1️⃣ **POST** `/api/register`

Registers a new user.
**Body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "pass123"
}
```

**Response (200 OK):**

```json
{
  "message": "User registered successfully",
  "status": 200
}
```

**Error (409 Conflict):**

```json
{
  "message": "Email already exists!"
}
```

---

#### 2️⃣ **POST** `/api/login`

Authenticates user and returns JWT token.
**Body:**

```json
{
  "email": "john@example.com",
  "password": "pass123"
}
```

**Response (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5...",
  "message": "Login successful",
  "status": 200
}
```

**Error (401 Unauthorized):**

```json
{
  "message": "Invalid email or password"
}
```

---

#### 3️⃣ **GET** `/api/test`

Simple test endpoint (no auth needed).
**Response:**

```text
testing is done
```

---

### 🟡 **CATEGORY CONTROLLER**

**Base URL:** `/api/categories`
**Requires JWT Token**

---

#### 4️⃣ **GET** `/api/categories/all`

Fetch all categories for logged-in user.
**Header:**

```
Authorization: Bearer <JWT_TOKEN>
```

**Response:**

```json
[
  { "id": 1, "name": "Food", "description": "Daily meals" },
  { "id": 2, "name": "Salary", "description": "Monthly income" }
]
```

---

#### 5️⃣ **POST** `/api/categories/add`

Add a new category.
**Header:**

```
Authorization: Bearer <JWT_TOKEN>
```

**Body:**

```json
{
  "name": "Entertainment",
  "description": "Movies, outings"
}
```

**Response:**

```json
{
  "id": 3,
  "name": "Entertainment",
  "description": "Movies, outings"
}
```

---

#### 6️⃣ **GET** `/api/categories/{name}`

Fetch category details by name.
**Example:** `/api/categories/Food`
**Response:**

```json
{
  "id": 1,
  "name": "Food",
  "description": "Daily meals"
}
```

---

#### 7️⃣ **PUT** `/api/categories/{name}`

Update category details.
**Example:** `/api/categories/Food`
**Body:**

```json
{
  "description": "Food & groceries"
}
```

**Response:**

```json
{
  "id": 1,
  "name": "Food",
  "description": "Food & groceries"
}
```

---

#### 8️⃣ **DELETE** `/api/categories/{name}`

Delete a category.
**Example:** `/api/categories/Entertainment`
**Response:**

```json
"Category deleted successfully"
```

---

### 🟣 **TRANSACTION CONTROLLER**

**Base URL:** `/api/transactions`
**Requires JWT Token**

---

#### 9️⃣ **POST** `/api/transactions/add`

Add new transaction.
**Header:**

```
Authorization: Bearer <JWT_TOKEN>
```

**Body:**

```json
{
  "amount": 1200,
  "type": "EXPENSE",
  "date": "2025-10-04",
  "note": "Dinner at restaurant"
}
```

**Query Param:**

```
categoryName=Food
```

**Response:**

```json
{
  "id": 10,
  "amount": 1200,
  "type": "EXPENSE",
  "date": "2025-10-04",
  "note": "Dinner at restaurant",
  "category": "Food"
}
```

---

#### 🔟 **GET** `/api/transactions/all`

Get all transactions of logged-in user.
**Response:**

```json
[
  {
    "id": 10,
    "category": "Food",
    "amount": 1200,
    "type": "EXPENSE",
    "date": "2025-10-04"
  }
]
```

---

#### 11️⃣ **GET** `/api/transactions/category?categoryName=Food`

Fetch transactions under one category.
**Response:**

```json
[
  {
    "id": 10,
    "amount": 1200,
    "type": "EXPENSE",
    "date": "2025-10-04"
  }
]
```

---

#### 12️⃣ **GET** `/api/transactions/byDate?start=2025-09-01&end=2025-09-30`

Fetch transactions by date range.
**Response:**

```json
[
  {
    "id": 8,
    "category": "Food",
    "amount": 500,
    "type": "EXPENSE",
    "date": "2025-09-20"
  }
]
```

---

#### 13️⃣ **GET** `/api/transactions/byType?type=INCOME`

Fetch transactions by type (INCOME or EXPENSE).
**Response:**

```json
[
  {
    "id": 5,
    "category": "Salary",
    "amount": 40000,
    "type": "INCOME",
    "date": "2025-09-01"
  }
]
```

---

### ⚙️ **Summary**

| Module      | Endpoint                     | Method | Auth |
| ----------- | ---------------------------- | ------ | ---- |
| Auth        | `/api/register`              | POST   | ❌    |
| Auth        | `/api/login`                 | POST   | ❌    |
| Auth        | `/api/test`                  | GET    | ❌    |
| Category    | `/api/categories/all`        | GET    | ✅    |
| Category    | `/api/categories/add`        | POST   | ✅    |
| Category    | `/api/categories/{name}`     | GET    | ✅    |
| Category    | `/api/categories/{name}`     | PUT    | ✅    |
| Category    | `/api/categories/{name}`     | DELETE | ✅    |
| Transaction | `/api/transactions/add`      | POST   | ✅    |
| Transaction | `/api/transactions/all`      | GET    | ✅    |
| Transaction | `/api/transactions/category` | GET    | ✅    |
| Transaction | `/api/transactions/byDate`   | GET    | ✅    |
| Transaction | `/api/transactions/byType`   | GET    | ✅    |

---
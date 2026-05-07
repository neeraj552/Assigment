# AI Document Assistant

An AI-powered document question-answering system built using Spring Boot and React.

The application allows users to upload PDF documents, extract text content, store chunked data in MySQL, and ask questions based on the uploaded document context.

---

# Features

- Upload PDF documents
- Extract text using Apache PDFBox
- Chunk large document text
- Store chunks in MySQL database
- Context-based question answering
- REST API architecture
- React frontend interface
- Docker support
- CI/CD pipeline using GitHub Actions

---

# Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Apache PDFBox
- Maven

## Frontend
- React
- Vite
- Axios

## DevOps
- Docker
- GitHub Actions (CI/CD)

---

# Project Architecture

```text
Frontend (React)
        ↓
Spring Boot Backend
        ↓
PDF Upload API
        ↓
PDF Text Extraction
        ↓
Text Chunking
        ↓
MySQL Database
        ↓
Chunk Retrieval
        ↓
Answer Generation
```

---

# Backend Setup

## Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/Assigment.git
cd assingment
```

---

## Configure Database

Create MySQL database:

```sql
CREATE DATABASE assingment;
```

---

## Configure Application Properties

Create:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=assignment

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/assingment
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=200MB
```

---

## Run Backend

```bash
./mvnw spring-boot:run
```

---

# Frontend Setup

Navigate to frontend folder:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run frontend:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# API Endpoints

## Upload PDF

```http
POST /api/files/upload
```

### Form Data
- file : PDF file

---

## Ask Question

```http
POST /api/files/chat
```

### Request Body

```json
{
  "fileId": 1,
  "question": "What is the theory about?"
}
```

### Response

```json
{
  "answer": "Theory of Reality...",
  "start": 0,
  "end": 30
}
```

---

# Docker Support

## Build Docker Image

```bash
docker build -t ai-assignment .
```

---

## Run Docker Container

```bash
docker run -p 8080:8080 ai-assignment
```

---

# CI/CD Pipeline

GitHub Actions workflow automatically:

- Builds the backend
- Runs tests
- Validates project on every push

Workflow file:

```text
.github/workflows/backend.yml
```

---

# Testing

Run tests:

```bash
./mvnw test
```

---

# Future Improvements

- AI model integration
- Audio/video transcription
- Semantic search
- Vector database integration
- Authentication & authorization
- Advanced chunk ranking

---

# Author

Neeraj Sharma

---

# License

This project is for educational and assignment purposes.

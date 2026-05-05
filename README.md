# PulseDesk - Comment to Ticket Triage System

A Spring Boot REST API which automaticly checks user comments using Hugging Face AI and converts them into support tickets when needed.

## How It Works

1. User submits a comment via UI
2. Comment is analyzed by Hugging Face AI (Llama model via Groq)
3. If the comment is a real issue or request - a ticket is automatically created with title, category, priority, and summary
4. If the comment is just a compliment - it is saved but no ticket is created

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- H2 in-memory database
- Hugging Face Inference API (Groq provider)
- JUnit 5 + Mockito (unit tests)
- Docker

## Setup Instructions

### 1. Get a Hugging Face API Token

- Register at [huggingface.co](https://huggingface.co)
- Go to Settings → Access Tokens → New Token
- Select type: **Read**
- Copy your token (starts with `hf_...`)

### 2. Configure the token

Open `src/main/resources/application.properties` and replace:
with your actual token.
You should see: YOUR_HF_TOKEN_HERE

or you can run with a docker
1).\mvnw clean package -DskipTests
2) .\mvnw docker build -t pulsedesk .
3) docker run -p 8080:8080 -e huggingface.api.token=YOUR_HF_TOKEN_HERE pulsedesk
#YOUR_HF_TOKEN_HERE which can you get from step(1. Get a Hugging Face API Token)

### 3. Run the application

```bash
.\mvnw spring-boot:run
```
From Intellij
run `IbmPuldeskApplication.java` directly from IntelliJ.


### 4. Open the UI

Go to: [http://localhost:8080](http://localhost:8080)

## API Endpoints

| Method | Endpoint      | Description          |
|--------|---------------|----------------------|
| POST   | /comments     | Submit a new comment |
| GET    | /comments     | Get all comments     |
| GET    | /tickets      | Get all tickets      |
| GET    | /tickets/{id} | Get ticket by ID     |

## Running Tests

3 unit tests covering TriageService logic:
- Comment with real issue → ticket created
- Compliment comment → no ticket
- AI returns null → no ticket

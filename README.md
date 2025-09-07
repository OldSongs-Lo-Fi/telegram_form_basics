# AdminBot 🤖

**AdminBot** is a Java framework for building advanced Telegram bots.
It is designed with a clear architecture separating **routes** (commands), **sessions** (user state), and **forms** (step-by-step dialogs).

---

## 🏗 Architecture

### 1. TelegramBot

The main service, extending `TelegramLongPollingBot`.

Responsibilities:

* Registering the bot with Telegram API ✅
* Receiving and processing incoming messages (`Update`) 💬
* Managing user sessions 🔑
* Delegating logic to registered routes 📍

Each `Route` is linked to a specific command in the bot.

---

### 2. Route

Interface for implementing bot commands:

```java
public interface Route {
    String getRoute();
    void process(Update update);
    void setTelegramBot(TelegramBot telegramBot);
}
```

* **getRoute()** — returns the command string (e.g., `/start`)
* **process(Update update)** — contains business logic
* **setTelegramBot(...)** — allows the route to send messages via the bot

**Example:** `StartRoute` saves a user and sends a welcome message.

---

### 3. Session

Represents the state of a user in the chat:

```java
public class Session {
    Map<String, Object> params = new ConcurrentHashMap<>();
    Form currentForm;
}
```

* Stores parameters between steps (`params`)
* Holds the active form if the user is in a multi-step dialog (`currentForm`)

---

### 4. Form

Abstract class for step-by-step user dialogs:

```java
public abstract class Form {
    private int currentQuestionId = 0;
    private final Map<String, Object> params = new ConcurrentHashMap<>();
    private final List<Question> questions;
    private final TelegramBot bot;

    public abstract void onInit(Update update);
    public abstract void onComplete(Update update);
}
```

* **onInit** — called at the start of the form (e.g., greeting) 👋
* **onComplete** — called when all questions are answered 🎯
* **processAnswer** — validates and stores user answers ✅
* **askCurrentQuestion** — sends the next question to the user ❓

---

### 5. Question

Represents a single step in a form:

```java
public abstract class Question {
    public abstract void askQuestion();
    public abstract void processAnswer(Update update, Map<String, Object> params) throws Exception;
    public abstract void processError(Exception e);
}
```

Each question defines:

* How to ask itself
* How to process the answer
* How to handle errors ⚠️

---

## ✨ Features

* Flexible **route/command system** via `Route`
* **User session management**
* **Step-by-step forms** for multi-stage interactions
* Reliable **message sending with retries** using `sendMessage`

---

## 🚀 Example Flow

`/start` command:

1. User sends `/start`
2. `StartRoute` is triggered
3. User data is saved to the database (`UserRepo`) 💾
4. Bot replies with **"Hello world!"** 🖐

---

## ⚡ Usage

1. Create a class implementing `Route`
2. Register it as a Spring component
3. Implement the logic in `process(Update update)`
4. Use `Form` + `Question` if you need multi-step dialogs

---

**AdminBot** makes building Telegram bots structured, maintainable, and ready for production 🚀

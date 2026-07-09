# 🚀 Logging Framework - Low Level Design (LLD)

A production-inspired **Logging Framework** built using **Java**, **Object-Oriented Design**, and **SOLID Principles**. This project demonstrates how modern logging libraries such as Log4j or SLF4J can be designed from scratch while maintaining scalability, flexibility, and maintainability.

> **Focus:** Low-Level Design • Design Patterns • Thread Safety • Extensible Architecture

---

## 📖 Overview

Logging is one of the most important components of any software system. A well-designed logging framework helps developers debug applications, monitor production systems, and analyze application behavior efficiently.

This project implements a modular logging framework that supports:

* Multiple log levels
* Configurable appenders
* Custom formatters
* Log filtering
* Runtime configuration
* Thread-safe logging
* Easy extensibility

The architecture follows industry-standard design principles, making it suitable as an educational LLD project and a strong portfolio addition.

---

# ✨ Features

* ✅ Five Log Levels

  * DEBUG
  * INFO
  * WARNING
  * ERROR
  * FATAL

* ✅ Priority-based logging

* ✅ Multiple Output Destinations

  * Console
  * File
  * Database

* ✅ Pluggable Formatters

* ✅ Custom Filters

* ✅ Runtime Configuration

* ✅ Thread-safe Logging

* ✅ Builder Pattern for Log Messages

* ✅ Easily Extendable Components

---

# 🏗️ System Architecture

```
                 +------------------+
                 |   Application    |
                 +--------+---------+
                          |
                          v
                  +---------------+
                  |    Logger     |
                  +-------+-------+
                          |
        +-----------------+-----------------+
        |                                   |
        v                                   v
+------------------+               +------------------+
|     Filters      |               |    Appenders     |
+------------------+               +--------+---------+
                                            |
              +-----------------------------+-----------------------------+
              |                             |                             |
              v                             v                             v
      ConsoleAppender               FileAppender               DatabaseAppender
              |                             |                             |
              +-------------+---------------+-----------------------------+
                            |
                            v
                     LogFormatter
                            |
               +------------+------------+
               |                         |
               v                         v
      SimpleFormatter         DetailedFormatter
```

---

# 📂 Project Structure

```
logging-framework/
│
├── src/
│
├── logger/
│   ├── Logger.java
│   └── LoggerImpl.java
│
├── appenders/
│   ├── LogAppender.java
│   ├── ConsoleAppender.java
│   ├── FileAppender.java
│   └── DatabaseAppender.java
│
├── formatter/
│   ├── LogFormatter.java
│   ├── SimpleFormatter.java
│   └── DetailedFormatter.java
│
├── filters/
│   ├── LogFilter.java
│   ├── LevelFilter.java
│   └── SourceFilter.java
│
├── configuration/
│   └── LogConfiguration.java
│
├── models/
│   ├── LogLevel.java
│   └── LogMessage.java
│
├── Main.java
│
└── README.md
```

---

# ⚙️ Core Components

## Logger

Acts as the central entry point for all logging operations.

Responsibilities:

* Accept log requests
* Validate log level
* Apply filters
* Forward logs to appenders

---

## LogMessage

Immutable object containing:

* Timestamp
* Log Level
* Message
* Source

Implemented using the **Builder Pattern**.

---

## LogAppender

Responsible for writing log messages to different destinations.

Implementations:

* ConsoleAppender
* FileAppender
* DatabaseAppender

Adding a new destination only requires implementing this interface.

---

## LogFormatter

Converts a LogMessage into a formatted string.

Implementations:

* SimpleFormatter
* DetailedFormatter

Supports customizable formatting patterns.

---

## LogFilter

Determines whether a log should be processed.

Examples:

* LevelFilter
* SourceFilter

Multiple filters can be chained together.

---

# 🔄 Logging Workflow

```
Application
      │
      ▼
Logger
      │
      ▼
Check Log Level
      │
      ▼
Apply Filters
      │
      ▼
Send to Appenders
      │
      ▼
Formatter
      │
      ▼
Console / File / Database
```

---

# 🧵 Thread Safety

The framework supports concurrent logging by:

* Synchronizing critical sections
* Using thread-safe collections
* Preventing race conditions
* Ensuring atomic logging operations

This allows multiple threads to safely log messages simultaneously.

---

# 🎯 Design Patterns

| Pattern                          | Purpose                               |
| -------------------------------- | ------------------------------------- |
| Strategy                         | Different Appenders and Formatters    |
| Builder                          | Creating immutable LogMessage objects |
| Chain of Responsibility          | Filter processing                     |
| Factory *(Optional Extension)*   | Creating appenders dynamically        |
| Singleton *(Optional Extension)* | Logger instance management            |

---

# 🧩 SOLID Principles

### Single Responsibility Principle

Each class has one clearly defined responsibility.

---

### Open/Closed Principle

New appenders, filters, and formatters can be added without modifying existing code.

---

### Liskov Substitution Principle

All implementations can replace their interfaces seamlessly.

---

### Interface Segregation Principle

Interfaces remain focused and lightweight.

---

### Dependency Inversion Principle

High-level modules depend on abstractions instead of concrete implementations.

---

# 📌 Functional Requirements

* Configurable log levels
* Multiple appenders
* Runtime configuration
* Custom formatting
* Log filtering
* Thread safety

---

# ⚡ Non-Functional Requirements

* High Performance
* Extensibility
* Scalability
* Maintainability
* Reliability
* Memory Efficiency

---

# 🚨 Edge Cases Handled

* Invalid log levels
* Concurrent logging
* File write failures
* Database failures
* Invalid formatter patterns
* Filter exceptions
* Runtime configuration changes

---

# 💻 Example Usage

```java
Logger logger = new LoggerImpl();

logger.setLevel(LogLevel.INFO);

logger.addAppender(new ConsoleAppender());

logger.addAppender(
    new FileAppender("application.log")
);

logger.info("Application Started");

logger.warning("Memory Usage High");

logger.error("Database Connection Failed");
```

---

# 🚀 Future Improvements

* Asynchronous Logging
* Rolling Log Files
* Log Rotation
* Email Appender
* REST API Appender
* Cloud Storage Appender
* JSON Formatter
* XML Formatter
* Kafka Appender
* ElasticSearch Integration
* Configuration using YAML/JSON
* Spring Boot Integration

---

# 🧪 Technologies Used

* Java
* Object-Oriented Programming
* Java Collections Framework
* Java Time API
* File I/O
* JDBC
* Design Patterns
* SOLID Principles

---

# 📚 Learning Outcomes

This project demonstrates:

* Low-Level System Design
* Object-Oriented Design
* Clean Architecture
* SOLID Principles
* Design Patterns
* Thread-safe Programming
* Extensible Software Design
* Real-world Logging Framework Architecture

---

# 🤝 Contributing

Contributions are welcome!

If you'd like to improve the project:

1. Fork the repository
2. Create a new feature branch
3. Commit your changes
4. Push the branch
5. Open a Pull Request

---

# 📄 License

This project is intended for educational and learning purposes. Feel free to use and extend it for personal or academic projects.

---

# 👨‍💻 Author

**Aditya Pansare**

Passionate about **Software Engineering**, **Low-Level Design**, **System Design**, and **Competitive Programming**.

If you found this project helpful, consider giving it a ⭐ to support the repository.

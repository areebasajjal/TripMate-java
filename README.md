# TripMate — Intelligent Travel Planning Desktop Application

TripMate is a Java-based desktop application that simplifies travel planning by providing a structured system to manage trips, destinations, and activities in one interface.

This project was developed as a **client-based software solution through a Preply tutoring engagement**, where real functional requirements were translated into a working desktop application.

It demonstrates practical use of:
- Object-Oriented Programming (OOP)
- Modular software design
- Java Swing GUI development
- File-based data persistence

---

## Project Context

TripMate was built as a **requirement-driven client project**, focused on converting real-world needs into a functional desktop system. The emphasis was on clean architecture, maintainability, and usable UI design.

---

## Key Features

- Trip creation and management system
- Structured handling of hotels, restaurants, places, and activities
- User login system for controlled access
- Dashboard-based navigation UI
- Persistent storage using Java serialization

---

## System Architecture

UI Layer (Java Swing)  
↓  
Controller Layer (Event Handling)  
↓  
Core Logic Layer (Trip & Entities)  
↓  
Persistence Layer (File Storage / Serialization)

---

## Tech Stack

- Java (Core)
- Java Swing
- OOP Principles
- File Handling (Serialization)

---

## Design Principles

- Encapsulation
- Modular decomposition
- Single Responsibility Principle
- Event-driven programming

---

## How to Run

```bash
git clone https://github.com/areebasajjal/TripMate-java.git
cd TripMate-java
javac src/ui/TripMate.java
java src.ui.TripMate
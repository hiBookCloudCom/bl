# Technical Documentation

## 1. Purpose of the Project
The purpose of this project is to demonstrate the design and implementation of a
cloud-native application using a microservices architecture.

This document provides a deeper technical insight into the system design and
architectural decisions and complements the information provided in `README.md`.

---

## 2. Architectural Design
The application is designed as a distributed system composed of independent
microservices.

Key architectural goals:
- Independent deployment and scalability
- Clear separation of responsibilities
- Cloud-native architecture

Communication between services is implemented using synchronous RESTful APIs over HTTP.

---


## 3. API Design Principles
The APIs are designed according to REST principles.

Design decisions:
- Use of standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`)
- Clear and predictable URL structure
- JSON as the standard data exchange format
- Stateless request handling

Each microservice exposes its own API surface and does not directly access the
internal implementation of other services.

---
## 4. Build Instructions

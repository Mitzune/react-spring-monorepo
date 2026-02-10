# 🚀 Spring Boot Starter

This Spring Boot application follows a **feature- and domain-based architecture**, which is recommended for scalability and adding new features without affecting existing code.

---

## 📁 Folder Structure

The project is organized as follows:

- **`controllers/`**  
  Handles HTTP requests and routes, acting as the entry point for the application.

- **`domains/`**  
  Contains all domain-related logic and data structures:
  - **`dto/`** – Data Transfer Objects used to transfer data between layers.  
  - **`entities/`** – JPA entities representing database tables.

- **`mappers/`**  
  Utility classes to convert between DTOs and entities (and vice versa).

- **`repositories/`**  
  Handles data access and manipulation, interacting directly with the database.

- **`services/`**  
  Service layer to encapsulate business logic and ensure safe operations across the application.

💡 **Note:**  
This structure ensures **separation of concerns**, **clean layering**, and makes it easy to **scale** the application by adding new controllers, services, or domain logic independently.

---

## 📦 Dependencies

The project uses the following dependencies for runtime, database, and testing:

### Core Spring Boot
- `spring-boot-starter-webmvc` – Provides web MVC and REST support.  
- `spring-boot-starter-data-jpa` – Enables JPA-based data persistence.  
- `spring-boot-h2console` – Provides an H2 database console for development.

### Databases
- `h2` (runtime) – In-memory database for development and testing.  
- `postgresql` (runtime) – PostgreSQL driver for production database connections.

### Testing
- `spring-boot-starter-data-jpa-test` (test) – Utilities for testing JPA repositories.  
- `spring-boot-starter-webmvc-test` (test) – Utilities for testing web controllers and MVC layer.

---

## ⚡ Running & Building

After building the project, the application can be run from the root-level `build/api/` folder:

```bash
# Navigate to build folder
cd build/api

# Run Spring Boot application
java -jar api.jar

```

## To do
- [ ] Testing layout for features implemented
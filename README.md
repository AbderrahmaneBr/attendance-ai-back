# Attendance AI - Backend
## Folder Structure
```
src/main/java/com/yourcompany/presencemanagement/
├── api/                       # API layer
│   ├── controller/            # REST controllers
│   ├── advice/                # Exception handlers and controller advices
│   ├── request/               # Request DTOs
│   └── response/              # Response DTOs
├── config/                    # Configuration classes
│   ├── security/              # Security configurations
│   └── swagger/               # API documentation
├── domain/                    # Domain model
│   ├── entity/                # JPA entities
│   ├── repository/            # Spring Data repositories
│   └── mapper/                # Entity-DTO mappers
├── exception/                 # Custom exceptions
├── service/                   # Business logic
│   ├── impl/                  # Service implementations
│   └── specification/         # JPA specifications for queries
└── util/                      # Utility classes
```

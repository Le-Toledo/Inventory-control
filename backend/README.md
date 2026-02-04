# Inventory Management - Backend (Kotlin + Spring Boot)

REST API desenvolvida em **Kotlin** com **Spring Boot** para gerenciamento de estoque e produção.

## 🚀 Tecnologias

- **Kotlin 1.9.21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (dev) / **PostgreSQL** (produção)
- **Maven**
- **JUnit 5 + Mockito**

## 📁 Estrutura

```
src/
├── main/
│   ├── kotlin/com/inventory/
│   │   ├── config/          # Configurações
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # Entidades JPA
│   │   ├── exception/       # Tratamento de exceções
│   │   ├── repository/      # Repositórios de dados
│   │   └── service/         # Lógica de negócio
│   └── resources/
│       └── application.properties
└── test/
    └── kotlin/com/inventory/
        └── service/         # Testes unitários
```

## ⚙️ Executar

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

## 🧪 Testes

```bash
mvnw.cmd test
```

## 📡 API Base URL

`http://localhost:8080/api`

## 🔍 H2 Console

`http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:inventory_db`
- User: `sa`
- Password: (vazio)

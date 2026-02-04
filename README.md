# 🏭 Sistema de Gerenciamento de Estoque e Produção

> Sistema full-stack moderno para gestão inteligente de estoque, controle de produtos, matérias-primas e cálculo automático de capacidade produtiva.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.21-purple.svg)](https://kotlinlang.org/)
[![React](https://img.shields.io/badge/React-18.2.0-blue.svg)](https://reactjs.org/)

---

## 🎯 Funcionalidades

✅ **Gestão de Produtos**

- Cadastro completo de produtos com valores
- Listagem e edição em tempo real
- Validações de dados robustas

✅ **Controle de Matérias-Primas**

- Gerenciamento de estoque
- Controle de quantidades disponíveis
- Histórico de movimentações

✅ **Associação Produto-Matéria-Prima**

- Vinculação de múltiplas matérias-primas por produto
- Definição de quantidades necessárias
- Cálculo automático de custos

✅ **Relatório de Produção Inteligente**

- Cálculo automático de capacidade produtiva
- Priorização por valor de produto
- Análise de viabilidade de produção em tempo real

---

## 🚀 INÍCIO RÁPIDO

### Pré-requisitos

- ☕ **Java JDK 17+** ([Baixar aqui](https://adoptium.net/))
- 📦 **Node.js 16+** ([Baixar aqui](https://nodejs.org/))
- 🔧 **Git** (opcional)

### Instalação Completa

#### 1️⃣ Instale o Java

```bash
# Baixe e instale o Java JDK 17 de: https://adoptium.net/
# Marque a opção "Add to PATH" durante a instalação

# Verifique a instalação:
java --version
```

📖 **Guia detalhado**: [INSTALL_JAVA.md](INSTALL_JAVA.md)

#### 2️⃣ Clone o Repositório

```bash
git clone <seu-repositorio>
cd teste-mao-na-massa
```

#### 3️⃣ Inicie o Sistema (Automático)

**Windows:**

```bash
.\start-all.bat
```

**Linux/Mac:**

```bash
chmod +x start-all.sh
./start-all.sh
```

#### 4️⃣ Acesse o Sistema

- 🌐 **Frontend**: http://localhost:3000
- 🔧 **Backend API**: http://localhost:8080
- 💾 **H2 Console**: http://localhost:8080/h2-console

---

## 🏗️ Arquitetura Técnica

### Backend (Spring Boot + Kotlin)

```
backend/
├── src/main/kotlin/com/inventory/
│   ├── controller/      # REST Controllers (API Endpoints)
│   ├── service/         # Lógica de Negócio
│   ├── repository/      # Acesso a Dados (JPA)
│   ├── entity/          # Modelos de Dados
│   ├── dto/             # Data Transfer Objects
│   └── exception/       # Tratamento de Erros
└── src/test/           # Testes Unitários
```

**Stack Técnico:**

- 🔹 **Spring Boot 3.2.0** - Framework principal
- 🔹 **Kotlin 1.9.21** - Linguagem moderna e concisa
- 🔹 **Spring Data JPA** - ORM e persistência
- 🔹 **H2 Database** - Banco em memória (dev)
- 🔹 **PostgreSQL** - Produção (configurável)
- 🔹 **Bean Validation** - Validações declarativas

### Frontend (React + Redux)

```
frontend/
├── src/
│   ├── pages/          # Páginas da aplicação
│   ├── components/     # Componentes reutilizáveis
│   ├── store/          # Redux Store e Slices
│   ├── services/       # Chamadas API
│   └── styles/         # CSS Modular
└── cypress/            # Testes E2E
```

**Stack Técnico:**

- 🔸 **React 18.2.0** - Biblioteca UI
- 🔸 **Redux Toolkit** - Gerenciamento de estado
- 🔸 **React Router v6** - Navegação SPA
- 🔸 **Axios** - Cliente HTTP
- 🔸 **Cypress** - Testes end-to-end

---

## 📡 API Endpoints

### Produtos

```http
GET    /api/products          # Lista todos os produtos
GET    /api/products/{id}     # Busca produto por ID
POST   /api/products          # Cria novo produto
PUT    /api/products/{id}     # Atualiza produto
DELETE /api/products/{id}     # Remove produto
```

### Matérias-Primas

```http
GET    /api/raw-materials         # Lista matérias-primas
GET    /api/raw-materials/{id}    # Busca por ID
POST   /api/raw-materials         # Cria nova matéria-prima
PUT    /api/raw-materials/{id}    # Atualiza matéria-prima
DELETE /api/raw-materials/{id}    # Remove matéria-prima
```

### Produção

```http
GET    /api/production/calculate  # Calcula possibilidades de produção
```

---

## 🧪 Testes

### Backend

```bash
cd backend
./mvnw test              # Roda todos os testes
./mvnw test -Dtest=ProductServiceTest  # Teste específico
```

### Frontend

```bash
cd frontend
npm test                 # Testes unitários (Jest)
npm run cypress:open     # Testes E2E (Cypress)
```

---

## 🛠️ Desenvolvimento

### Rodando Separadamente

**Backend:**

```bash
cd backend
.\mvnw.cmd spring-boot:run    # Windows
./mvnw spring-boot:run         # Linux/Mac
```

**Frontend:**

```bash
cd frontend
npm install
npm start
```

### Variáveis de Ambiente

Crie `backend/src/main/resources/application-local.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

## 📋 Requisitos do Projeto

✅ **CRUD Completo** de Produtos e Matérias-Primas  
✅ **Associação** Produto ↔ Matéria-Prima  
✅ **Cálculo Automático** de Possibilidades de Produção  
✅ **Priorização** por Valor do Produto  
✅ **Validações** em Backend e Frontend  
✅ **Responsividade** Mobile-First  
✅ **Testes** Unitários e E2E  
✅ **Documentação** Completa

---

## 🐛 Troubleshooting

### Erro: "JAVA_HOME is not set"

```bash
# Configure manualmente:
export JAVA_HOME=/caminho/para/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

### Porta 8080 já em uso

```bash
# Encontre o processo:
netstat -ano | findstr :8080

# Finalize o processo (substitua PID):
taskkill /PID <PID> /F
```

### Dependências npm com vulnerabilidades

```bash
cd frontend
npm audit fix
```

---

## 📦 Deploy

### Backend (Heroku)

```bash
cd backend
heroku create meu-app-inventory
git push heroku main
```

### Frontend (Vercel/Netlify)

```bash
cd frontend
npm run build
# Suba a pasta 'build' no serviço de hospedagem
```

---

## 👨‍💻 Autor

Desenvolvido por **[Seu Nome]**  
📧 Email: seu.email@exemplo.com  
🔗 LinkedIn: [Seu LinkedIn](https://linkedin.com/in/seu-perfil)  
💼 GitHub: [@seu-usuario](https://github.com/seu-usuario)

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🙏 Agradecimentos

Projeto desenvolvido como desafio técnico para demonstração de habilidades em:

- Desenvolvimento Full-Stack
- Arquitetura de Software
- Boas Práticas de Código
- Testes Automatizados
- Documentação Técnica

---

<div align="center">
  <p>⭐ Se este projeto foi útil, considere dar uma estrela!</p>
  <p>Feito com ❤️ e muito ☕</p>
</div>

### Non-Functional Requirements

- ✅ RNF001: Web platform (Chrome, Firefox, Edge compatible)
- ✅ RNF002: API architecture (Backend/Frontend separation)
- ✅ RNF003: Responsive design
- ✅ RNF004: PostgreSQL database
- ✅ RNF005: Spring Boot framework
- ✅ RNF006: React + Redux
- ✅ RNF007: English codebase

### Functional Requirements

- ✅ RF001: Product CRUD (Backend)
- ✅ RF002: Raw Material CRUD (Backend)
- ✅ RF003: Product-RawMaterial association (Backend)
- ✅ RF004: Production calculation (Backend)
- ✅ RF005: Product CRUD (Frontend)
- ✅ RF006: Raw Material CRUD (Frontend)
- ✅ RF007: Product-RawMaterial association (Frontend)
- ✅ RF008: Production report (Frontend)

### Additional Features

- ✅ Unit tests (Backend)
- ✅ Unit tests (Frontend)
- ✅ Integration tests (Cypress)
- ✅ Exception handling
- ✅ Input validation
- ✅ CORS configuration

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Database Setup

1. Create PostgreSQL database:

```sql
CREATE DATABASE inventory_db;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE inventory_db TO postgres;
```

2. The application will automatically create tables on first run (Hibernate DDL auto-update).

### Backend Setup

1. Navigate to backend directory:

```bash
cd backend
```

2. Configure database in `src/main/resources/application.properties` (if needed):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

3. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

4. Run tests:

```bash
mvn test
```

### Frontend Setup

1. Navigate to frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start development server:

```bash
npm start
```

The frontend will start on `http://localhost:3000`

4. Run tests:

```bash
npm test
```

5. Run Cypress integration tests:

```bash
npx cypress open
```

## 📚 API Documentation

### Products API

**GET /api/products**

- Returns all products with their raw materials

**GET /api/products/{id}**

- Returns a specific product

**POST /api/products**

- Creates a new product
- Body:

```json
{
  "name": "Chair",
  "value": 150.0,
  "rawMaterials": [
    {
      "rawMaterialId": 1,
      "quantityRequired": 10
    }
  ]
}
```

**PUT /api/products/{id}**

- Updates an existing product

**DELETE /api/products/{id}**

- Deletes a product

### Raw Materials API

**GET /api/raw-materials**

- Returns all raw materials

**GET /api/raw-materials/{id}**

- Returns a specific raw material

**POST /api/raw-materials**

- Creates a new raw material
- Body:

```json
{
  "name": "Steel",
  "stockQuantity": 1000
}
```

**PUT /api/raw-materials/{id}**

- Updates an existing raw material

**DELETE /api/raw-materials/{id}**

- Deletes a raw material

### Production API

**GET /api/production/calculate**

- Calculates which products can be produced
- Returns production suggestions prioritized by value
- Response:

```json
{
  "suggestions": [
    {
      "productId": 1,
      "productName": "Chair",
      "quantityCanProduce": 10,
      "productValue": 150.0,
      "totalValue": 1500.0
    }
  ],
  "totalValue": 1500.0
}
```

## 🗄️ Database Schema

### products

- id (PK)
- name
- value

### raw_materials

- id (PK)
- name
- stock_quantity

### product_raw_materials

- id (PK)
- product_id (FK)
- raw_material_id (FK)
- quantity_required

## 🎨 Features

### Product Management

- Create, view, update, and delete products
- Set product values
- Associate multiple raw materials with quantities

### Raw Material Management

- Create, view, update, and delete raw materials
- Track stock quantities
- Real-time inventory updates

### Production Calculation

- Automatic calculation of producible items
- Prioritization by product value
- Optimal raw material allocation
- Visual production report with total values

## 🧪 Testing

### Backend Tests

Located in `backend/src/test/java/com/inventory/`

- Unit tests for all service layers
- Test coverage for CRUD operations
- Production calculation logic testing

### Frontend Tests

- Unit tests: `npm test`
- Integration tests: `npx cypress open`
- Coverage for components and Redux store

## 📱 Responsive Design

The application is fully responsive and works on:

- Desktop (1200px+)
- Tablet (768px - 1199px)
- Mobile (< 768px)

## 🔒 Security Features

- Input validation on both frontend and backend
- SQL injection prevention through JPA
- CORS configuration for secure API access
- Error handling with meaningful messages

## 🛠️ Technology Stack

### Backend

- Spring Boot 3.2.0
- Spring Data JPA
- Spring Web
- PostgreSQL Driver
- Lombok
- JUnit 5
- Mockito
- Maven

### Frontend

- React 18.2.0
- Redux Toolkit 2.0.1
- React Router 6.20.1
- Axios 1.6.2
- Cypress 13.6.2
- Jest
- React Testing Library

## 📝 Code Quality

- Clean code architecture
- SOLID principles
- RESTful API design
- Component-based frontend
- Redux for state management
- Separation of concerns

## 🚢 Deployment

### Backend

```bash
cd backend
mvn clean package
java -jar target/inventory-management-1.0.0.jar
```

### Frontend

```bash
cd frontend
npm run build
# Deploy the 'build' folder to any static hosting service
```

## 👨‍💻 Development

### Adding New Features

1. Backend:
   - Add entity in `entity/`
   - Create repository in `repository/`
   - Implement service in `service/`
   - Add controller in `controller/`
   - Write tests in `test/`

2. Frontend:
   - Create Redux slice in `store/slices/`
   - Add service method in `services/`
   - Create components in `components/` or `pages/`
   - Add routes in `App.js`
   - Write tests

## 📄 License

This project was created as a technical assessment.

## 👤 Author

Created for Autoflex full-stack developer position assessment.

## 📞 Support

For questions or issues, please refer to the code documentation or create an issue in the repository.

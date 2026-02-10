# 🚀 GUIA RÁPIDO - Solução de Problemas

## ⚠️ ERRO PRINCIPAL: Java não está instalado

### ✅ SOLUÇÃO EM 3 PASSOS:

---

## 📥 PASSO 1: Instalar Java JDK 17

### Baixe e Instale:

1. **Acesse**: https://adoptium.net/temurin/releases/
2. **Escolha**:
   - Version: **17**
   - Operating System: **Windows**
   - Architecture: **x64**
   - Package Type: **JDK**
3. **Baixe** o arquivo `.msi`
4. **Execute** o instalador
5. ✅ **MARQUE** a opção "**Add to PATH**" (muito importante!)
6. Clique **Next** até finalizar

---

## 🔄 PASSO 2: Reinicie o Terminal

**IMPORTANTE**: Após instalar o Java:

1. **Feche** todos os terminais PowerShell abertos
2. Abra um **NOVO** terminal PowerShell
3. Teste se funcionou:

```powershell
java -version
```

Deve aparecer algo como:

```
openjdk version "17.0.10" 2024-01-16
```

Se aparecer "java não é reconhecido", o Java não foi instalado corretamente.

---

## 🚀 PASSO 3: Executar o Sistema

### A) Backend (Terminal 1):

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Aguarde até ver: **"Started InventoryManagementApplication"**

### B) Frontend (Terminal 2):

Abra OUTRO terminal e execute:

```powershell
cd frontend
npm start
```

O navegador abrirá automaticamente em: **http://localhost:3000**

---

## 🎯 Alternativa Rápida

Se o Java já estiver instalado:

```powershell
.\start-all.bat
```

Este comando inicia backend e frontend automaticamente.

---

## 🐛 Erros Comuns e Soluções

### ❌ "java não é reconhecido"

**Causa**: Java não foi instalado ou PATH não configurado  
**Solução**:

1. Instale o Java JDK 17 de https://adoptium.net/
2. Durante instalação, MARQUE "Add to PATH"
3. REINICIE o terminal

### ❌ "mvnw.cmd não é reconhecido"

**Causa**: Você não está na pasta `backend`  
**Solução**: Execute `cd backend` primeiro

### ❌ "npm não é reconhecido"

**Causa**: Node.js não está instalado  
**Solução**: Instale de https://nodejs.org/

### ❌ Porta 8080 já em uso

**Causa**: Outro programa usando a porta  
**Solução**:

```powershell
# Ver processo na porta 8080
netstat -ano | findstr :8080

# Matar processo (substitua PID)
taskkill /PID <número_do_pid> /F
```

### ❌ Frontend não conecta ao backend

**Causa**: Backend não está rodando  
**Solução**:

1. Verifique se backend iniciou: http://localhost:8080
2. Veja se há erros no console do backend

---

## ✅ Sistema Funcionando

Quando tudo estiver correto:

- ✅ **Backend API**: http://localhost:8080
- ✅ **Frontend**: http://localhost:3000
- ✅ **H2 Console**: http://localhost:8080/h2-console

---

## 📋 Checklist de Verificação

Antes de executar, confirme:

- [ ] Java JDK 17+ instalado (`java -version`)
- [ ] Node.js instalado (`node -version`)
- [ ] Dependências frontend instaladas (`npm install` na pasta frontend)
- [ ] Está na pasta correta (backend ou frontend)
- [ ] Terminal foi reiniciado após instalar Java

---

## 🆘 Ainda com problemas?

1. Certifique-se que instalou **JDK** (não apenas JRE)
2. Verifique se marcou "Add to PATH" na instalação
3. **REINICIE** o terminal após instalar
4. Execute `java -version` para confirmar
5. Veja os logs de erro no terminal

---

**Leia também**: [INSTALL_JAVA.md](INSTALL_JAVA.md) para instruções detalhadas do Java

```bash
cd frontend
npm install
npm start
```

Frontend runs on: http://localhost:3000

## 4. First Steps

1. Access http://localhost:3000
2. Navigate to "Materias-Primas"
3. Add some Materias-Primas (e.g., Steel, Aluminum, Wood)
4. Navigate to "Products"
5. Create products and associate Materias-Primas
6. View "Production Report" to see what can be produced

## Testing

### Backend Tests

```bash
cd backend
mvn test
```

### Frontend Tests

```bash
cd frontend
npm test
```

### Integration Tests

```bash
cd frontend
npx cypress open
```

## Troubleshooting

**Database Connection Error:**

- Ensure PostgreSQL is running
- Check credentials in `backend/src/main/resources/application.properties`

**Port Already in Use:**

- Backend: Change `server.port` in application.properties
- Frontend: Set PORT environment variable

**CORS Issues:**

- Verify backend CORS configuration in `WebConfig.java`
- Ensure frontend API URL matches backend URL


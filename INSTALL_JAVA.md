# 🚨 INSTALAÇÃO DO JAVA JDK

O Java JDK 17 ou superior é **obrigatório** para executar o backend.

## 📥 Download e Instalação

### Opção 1: Eclipse Temurin (Recomendado)

1. Acesse: https://adoptium.net/
2. Baixe o **JDK 17** ou **JDK 21** (Windows x64)
3. Execute o instalador
4. ✅ Marque a opção "**Add to PATH**" durante a instalação
5. Clique em "Next" e aguarde a instalação

### Opção 2: Oracle JDK

1. Acesse: https://www.oracle.com/java/technologies/downloads/#java17
2. Baixe o Windows x64 Installer
3. Execute e siga as instruções
4. Adicione manualmente ao PATH se necessário

### Opção 3: Via Winget (Linha de Comando)

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
```

## ✅ Verificar Instalação

Após instalar, **feche e abra novamente o terminal PowerShell** e execute:

```powershell
java -version
```

Deve aparecer algo como:

```
openjdk version "17.0.x" 2024-xx-xx
OpenJDK Runtime Environment Temurin-17.0.x
```

## 🎯 Configurar PATH Manualmente (se necessário)

Se o comando `java -version` não funcionar:

1. Procure "Variáveis de Ambiente" no Windows
2. Em "Variáveis do Sistema", encontre "Path"
3. Clique em "Editar"
4. Adicione o caminho da instalação Java (exemplo: `C:\Program Files\Eclipse Adoptium\jdk-17.0.x\bin`)
5. Clique OK e **reinicie o terminal**

## 🚀 Após Instalar o Java

Execute os comandos:

```powershell
# Navegue até a pasta do backend
cd backend

# Execute o backend
mvnw.cmd spring-boot:run
```

O servidor iniciará em: http://localhost:8080

---

## ⚡ Executar Todo o Sistema

Uma vez que o Java esteja instalado, use os scripts:

```powershell
# Instalar dependências
.\setup.bat

# Iniciar backend e frontend juntos
.\start-all.bat
```

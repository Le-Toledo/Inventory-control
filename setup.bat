@echo off
echo ============================================
echo Inventory Management System - Setup
echo ============================================
echo.

echo [1/4] Checking Java installation...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed!
    echo Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)
echo Java is installed!
echo.

echo [2/4] Checking Node.js installation...
node --version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Node.js is not installed!
    echo Please install Node.js from: https://nodejs.org/
    pause
    exit /b 1
)
echo Node.js is installed!
echo.

echo [3/4] Checking Maven installation...
call mvn --version
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Maven is not installed. Installing Maven...
    echo Please download Maven from: https://maven.apache.org/download.cgi
    echo And add it to your PATH, or install it using:
    echo   winget install Apache.Maven
    echo.
    echo Alternatively, you can use the Maven Wrapper (mvnw) in the backend folder.
    pause
)
echo.

echo [4/4] Installing frontend dependencies...
cd frontend
call npm install
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to install frontend dependencies!
    pause
    exit /b 1
)
cd ..
echo.

echo ============================================
echo Setup completed successfully!
echo ============================================
echo.
echo Next steps:
echo 1. Start the backend:  cd backend ^&^& mvnw.cmd spring-boot:run
echo 2. Start the frontend: cd frontend ^&^& npm start
echo 3. Access the app at:  http://localhost:3000
echo.
pause

@echo off
echo ============================================
echo Starting Complete Inventory Management System
echo ============================================
echo.
echo This will start both backend and frontend servers.
echo Backend will run on: http://localhost:8080
echo Frontend will run on: http://localhost:3000
echo.
echo Press Ctrl+C to stop the servers.
echo.
pause

start "Backend Server" cmd /k "cd backend && mvnw.cmd spring-boot:run || mvn spring-boot:run"
timeout /t 10 /nobreak

start "Frontend Server" cmd /k "cd frontend && npm start"

echo.
echo Both servers are starting in separate windows...
echo Wait for them to fully start, then access: http://localhost:3000
echo.

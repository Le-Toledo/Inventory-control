@echo off
echo ============================================
echo Starting Inventory Management Frontend
echo ============================================
echo.

cd frontend

if not exist node_modules (
    echo Installing dependencies...
    call npm install
)

echo Starting React development server...
call npm start

@echo off
echo ============================================
echo Starting Inventory Management Backend
echo ============================================
echo.

cd backend

echo Checking for Maven...
call mvn --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Using Maven...
    call mvn spring-boot:run
) else (
    echo Maven not found. Using Maven Wrapper...
    if exist mvnw.cmd (
        call mvnw.cmd spring-boot:run
    ) else (
        echo ERROR: Neither Maven nor Maven Wrapper found!
        echo Please run setup.bat first or install Maven.
        pause
        exit /b 1
    )
)

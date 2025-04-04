@echo off
echo Compiling Java files...
javac *.java

if %errorlevel% neq 0 (
    echo Compilation failed. Please check your Java installation.
    pause
    exit /b
)

echo Compilation successful.
echo.
echo Select the demo to run:
echo 1. Basic File Handling Demo
echo 2. Advanced File Operations Demo
echo.
set /p choice="Enter your choice (1 or 2): "

if "%choice%"=="1" (
    echo.
    echo Running File Handling Demo...
    echo.
    java FileHandlingDemo
) else if "%choice%"=="2" (
    echo.
    echo Running Advanced File Operations Demo...
    echo.
    java AdvancedFileOperations
) else (
    echo.
    echo Invalid choice. Running basic demo by default...
    echo.
    java FileHandlingDemo
)

pause 
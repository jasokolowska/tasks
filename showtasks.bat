call .\runcrud
if "%ERRORLEVEL%" == "0" goto browser
echo.
echo Blad kompilacji runcrud.bat

:browser
start chrome "http://localhost:8080/crud/v1/task/getTasks"
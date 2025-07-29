# Krainet - Руководство по запуску.

Предварительные требования:

    Java 17 или новее
    Maven 3.8+ (для сборки)
    PostgreSQL 14+ (уже настроенная БД)
    Docker (опционально)

Установка и запуск

1. Сборка проекта

        mvn clean package

После сборки в папке target будет создан исполняемый файл:

krainet-0.0.1-SNAPSHOT.jar

2.Настройка базы данных

    PostgreSQL запущен на localhost:5432
    Существует БД krainet
    Создан пользователь:

    CREATE USER anton WITH PASSWORD '1234';
    GRANT ALL PRIVILEGES ON DATABASE krainet TO anton;

3.Запуск приложения

    java -jar target/krainet-0.0.1-SNAPSHOT.jar

Сервер запустится на порту 8081.

# Доступ к системе

Система будет доступна по адресу:
🌐 http://localhost:8081

Тестовые пользователи (логин → пароль):

| Роль       | Логин       | Пароль    | Доступ               |
|------------|-------------|-----------|----------------------|
| **ADMIN**  | `admin`     | `admin`   | Полные права         |
| **ADMIN**  | `boss`      | `boss`    | Полные права         |
| **USER**   | `staff`     | `staff`   | Ограниченный доступ  |
| **USER**   | `heisenberg`| `user`    | Ограниченный доступ  |


# Конфигурация системы

Основные параметры (из application.yml):

База данных

    spring.datasource:
    url: jdbc:postgresql://localhost:5432/krainet
    username: anton
    password: "1234"

Сервер

    server:
    port: 8081

Другие настройки

    flyway:
    locations: classpath:db/migration
    mail:
    host: localhost
    port: 1027

# Устранение неполадок

1. Ошибка подключения к БД:

    psql -h localhost -U anton -d krainet

    Проверьте доступность БД.

2. Миграции Flyway:

    Проверьте наличие SQL-файлов в src/main/resources/db/migration

    Логи миграций выводятся при запуске приложения

3. Почтовый сервер:

    Приложение ожидает SMTP-сервер на localhost:1027 (для тестов можно использовать MailHog)

# Docker-развертывание

1. Запустите PostgreSQL:

        docker run --name krainet-db -e POSTGRES_USER=anton -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=krainet -p 5432:5432 -d postgres:14


2. Соберите и запустите приложение:

        mvn clean package
        docker build -t krainet-app .
        docker run -p 8081:8081 --name krainet-app --link krainet-db -d krainet-app
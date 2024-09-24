# Указываем базовый образ
FROM openjdk:21-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл в контейнер
COPY target/admin-server-0.0.1-SNAPSHOT.jar /app/admin-server-0.0.1-SNAPSHOT.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/admin-server-0.0.1-SNAPSHOT.jar"]
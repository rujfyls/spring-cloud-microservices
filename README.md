<h1>Deposit service</h1>

____________________________________________________

<h5>Депозит денег на счёт.</h5>
Данный проект разработан в учебных целях, для знакомства с новыми технологиями микропроцессорной архитектуры.

_____________________________________________________

**Технологии**

 - Spring Boot
 - Hibernate
 - Docker (Docker Compose)
 - Gradle
 - RabbitMQ
 - PostgreSQL
 - Mockito
 - Spring Cloud
 - Eureka
 - Zuul
 - Feign

_______________________________________________________

**Архитектура**

![architecture](https://user-images.githubusercontent.com/86868993/160143037-82463054-aad2-4018-af18-7bf8da46988a.png)


**Бизнес логика**

![bisnes-logic](https://user-images.githubusercontent.com/86868993/160143078-003033c8-b0d9-48d9-b29c-95bfc8876d45.png)


**Диаграмма базы данных**

![db](https://user-images.githubusercontent.com/86868993/160143134-e7d8d8f7-c061-443c-b8ba-64536e1afbf8.png)


______________________________________________________


**Запуск приложения**

1. Скачать RabbitMQ коммандой в Docker "docker run -p 15672:15672 -p 5672:5672 rabbitmq:3-management"
2. Далее собираем наш образ "docker-compose build"
3. Запускаем наш образ "docker-compose up"



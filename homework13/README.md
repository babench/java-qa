# Домашнее задание #13: Spring

##Создать тесты на основе spring test для проекта на основе spring boot

Цель: Студент учится использовать Spring, Spring Test и Spring Boot Test фреймворки на примере написания unit тестов для типично приложения.
- [X] Сделайте fork проекта с калькулятором, который мы разбирали на занятии [github](https://github.com/PokimonZerg/otus-junit5-springboot)
- [X] Напишите необходимое количество unit тестов для этого проекта, используя spring test и/или spring boot test фреймворки.

Критерии оценки: Максимальная оценка 3 балла
+ 1 балл за имлементацию тестов
+ 1 балл вы получите за качество решения (production ready код)
+ 1 балл за скорость выполнения тестов (проще говоря ваши тесты не используют реальный контейнер сервлетов)

## Подробности реализации

1. Исправлена ошибка multiply сервиса.
2. Возможные операции вынесены в enum.
3. К проекту подключен Swagger для визуализации возможных методов сервис. [Swagger UI link](http://localhost:8080/swagger-ui.html#/calculator-controller/calcUsingGET).
Пример приложен в .\docs.
4. Тестирование через SpringBootTest и RestAssured реализовано в CalculatorTest.
5. Быстрое тестирование реализовано в CalculatorServiceTest. Тестовые данные заносятся через Json Data provider JSONDataProvider. Ресурсы теста расположены в 
test\resources\testdata\CalculatorServiceTest\TestData.json


## Фрагменг mvn clean test

[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.453 s - in TestSuite
2019-07-20 14:04:00.339  INFO 13440 --- [       Thread-2] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
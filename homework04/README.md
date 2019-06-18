# Домашнее задание #04

##Создание нового тест-сьюита

Цель:

- [X] Создать тест прохождения тест-кейса
- [X] Цель: Научиться писать тесты используя css элементы
- [X] Написать следующий кейс: 
- [X] 1. Открыть тест-линк -> Прогнать тесты
- [X] 2. Выбрать любой тест, кликнуть на него
- [X] 3. Проверить что в заголовке теста цвет черный
- [X] 4. Проставить "Пройден" во всех шагах
- [X] 5. Нажать на "passed"
- [X] 6. Проверить что цвет в заголовке теста поменялся на зеленый
- [X] 7. Проверить что цвет в дереве тестов поменялся на зеленый
- [X] 8. "Провалить" тест
- [X] 9, 10 Проверить что цвет стал красным

- [X] Доделать задание с урока:
- [X] Открыть TestLink
- [X] Test Project Management
- [X] Create button
- [X] Заполнить форму
- [X] Сохранить
- [X] Проверить, что в таблице ваш проект с настройками (имя, описание, public)

Критерии оценки: 5 баллов за задание
 
## Docker
Произвести запуск Test Link через docker-compose.yml

## Подробности реализация
 1. Создать Project c prefix JAQA.
 2. Создать Test Plan
 2. Нагенерить Test Suite, использую TestSuiteTest
 3. Создать Build, назначить тесты на исполнение! 
 
 4. Главные тесты ДЗ расположены в ExecuteTestTest.class
  4.1 shouldProcessPositiveTestCase
  пример работы после теста:
  .\doc\ExecuteTestTest.shouldProcessPositiveTestCase.Passed.png
  
  4.2 shouldProcessNegativeTestCase
  пример работы 
  .\doc\ExecuteTestTest.shouldProcessNegativeTestCase.Failed.png
  
 Добавка по Test Project Management
 Тест реализован в TestPlanManagementTest.class. Пример работы в
 .\doc\TestPlanManagementTest.results.png
﻿# Домашнее задания #19: CI

- [X] Доработать CI для удобной отчетности, выполнения по push в git и выполнять back-up конфигураций

- [X] Запустите и настройте локально Jenkins (сервис или контейнер)

- [X] Создайте job

# Шаги в job

 1. Выгрузить из вашего репозитория код тестов.
 
 2. Собрать проект.
 
 3. Выполнить все тесты.
 
 4. Прислать письмо вам на почту. В письме указаны
 
- номер сборки
-- статус сборки
- ветка репозитория, из которой был взят код тестов
- количество тестов (всего/успешных/проваленных/пропущенных)
- общее время выполнения job'ы


- Настройте job так, чтобы она запускалась после каждого git push'а в ваш репозиторий (использовать webhooks) и каждую ночь в 01:00.

* Помимо отчетности по e-mail, отчет должен приходить в канал в slack

* Отчеты должны добавляться в систему отчетов (на ваш выбор allure, report portal и подобные)

* По окончанию выполнения job, должен выполняться back-up самой job'ы и настроек (можно использовать SCM Sync configuration plugin)

## Подробности реализация

Тесты для запуска взял из HW06 (про yandex market).
См. детали настроек и реализации в [report](.\docs\Homework_19_Zaikin.docx).
﻿# Домашнее задание #14: Работа с GitHub через REST API

##Цель: Научиться пользоваться таким инструментом, как POSTMAN и делиться результатами с коллегами.

##TODO:
- [X] Создать коллекцию REST запросов в POSTMAN для работы с репозиторием на GitHub.
- [X] Cоздание Issue
- [X] Добавить комментарий к Issue
- [X] Добавить тег
- [X] Перевести в статус "завершена"

Результат оформить в виде коллекции в POSTMAN.
Не забудьте использовать Environment и переменные в запросах, чтобы любой человек мог использовать вашу коллекцию для своего проекта.
Результат прислать в виде ссылки на коллекцию.

Критерии оценки:

1 - Создается Issue

2 - Ей присваивается комментарий

3 - Ей присваивается тег

4 - Её статус меняется

5 - Используется environment в POSTMAN, чтобы я легко мог исполнить запросы у себя для своего проекта.


Таким образом максимальная оценка 5 баллов.
Рекомендуем сдать до: 15.08.2019

## Подробности реализации

1. Создана коллекция запросов [link](.\docs\Otus Java QA- HW-14- Alexander Zaikin.postman_collection.json)
2. Создан enviroment для установки параметров в запросах  [link](.\docs\Github.postman_environment.json). Перед началом работ нужно установить параменты в Envinroment github.
 
 + USER_NAME - пользователь, чьи репозитории просматриваем.
 + REPO_NAME - репозиторий пользователя.
 
 + В коллекции нужно установить параметры авторизации пользователя на сайте GitHub.

В запросах авторизация наследуется от коллекции.
Альтернативно, делал через переменную ACCESS_KEY в Envinroment. Но тогда нужно в каждом запросе, требующим авторизацию, ставить это переменную.
При большом количестве запросов удобнее централизованно управлять политикой авторизации.

3. Коллекция включает следующие запросы на поиск репозиториев пользователя, добавление issue в нужный репозиторий. Изменение, установку Label и закрытие issue.
После создания нового issue номер сохраняется в переменную окружения issue_number для подстановки в следующие запросы.

Ссылка на коллекцию [link](https://www.getpostman.com/collections/cc1c50ad07132b2a97a1).
Ссылка для добавления в Team [link](https://app.getpostman.com/join-team?invite_code=ec358ae42e50af45d4c67749595727c6&ws=c4d5f0bf-306b-4d03-8426-1f08d14792ef).

## Дополнения

В папке docs находятся:

+ [Github.postman_environment](.\docs\Github.postman_environment.json)
+ [Otus Java QA- HW-14- Alexander Zaikin.postman_collection](.\docs\Otus Java QA- HW-14- Alexander Zaikin.postman_collection.json)
+ Sceenshots: Environment, Api, TestCase {create -> comment -> change tag -> close}


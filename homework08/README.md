﻿# Домашнее задание #08: Web crawler (*)

## Цель
Задание проектного типа

Его не обязательно сдавать к следующему занятию и может занять у вас больше 2х часов. 
 
## Задача: 
Реализовать crawler [Wikipedia](https://en.wikipedia.org/wiki/Web_crawler), который будет открывать конкретный сайт и полностью проходить все его страницы для достижения конкретной цели.

## Примеры: 
 1. https://www.drive2.ru/cars/?sort=selling - собрать все объявления о продаже в csv-файл (ссылка, год автомобиля, цена, марка, модель, объем двигателя) 
 2. https://www.mann-ivanov-ferber.ru/books/allbooks/?booktype=audiobook - пройти по страницам всех аудиокниг, собрать в в csv-файл (ссылка, название, автор, цена, ссылку для скачивания ознакомительного фрагмента) 
 3. https://otus.ru/ - пройти по всем страницам сайта и проверить, что отображение страниц корректно (отображается шапка сайта, лого и т.д.). Все ссылки, ведущие на сторонние ресурсы (соцсети, блоги и т.д.) записать в отдельный файл 
 4. Свой ресурс. Своя цель Задание нацелено на оттачивание знаний и навыков по работе с инструментом, и умение прорабатывать алгоритмы 
 
## Предлагаю использовать гибкий подход в разработке: 
-[ ] выделите в своем проекте самую важную часть, реализуйте ее и сдайте на проверку.
-[ ] В следующий спринт добавьте наиболее приоритетную функциональность и снова "в релиз".

Критерии оценки: 12 баллов

## Подробности реализации
1. В качестве сайта выбрал drive2. Собираю информацию о Mazda.
2. Сканирование сайта проходит за 2 этапа
 2.1 Включает сканирование карточек автомобилей для запоминания href объявления. Данная операция работает в один поток.
 2.2 Второе сканирование включает в себя открывание страниц объявлений и считывание информации об автомобиле. Данная работа проходит в 4 потока
 
3. Модели данных представлены в пакете entity. Везде ключевое поле long. Dao реализован в HibernateDao.
4. Хранение данные осуществляют в H2 базе
5. Параллельный скан (п.2.2) реализован в виде forkjoin задачи. Объекты расположены в пакетe forkjoin

## Порядок запуска
1. установить локальную H2 и запустить
2. Установить в HibernateConfigurationHelper hibernate.hbm2ddl.auto в режим create-drop
3. Запустить в тестовом классе IndexPageTest метод shouldSaveCarsHref для сохранения карточек с ссылками на автомобили
4. Установить в HibernateConfigurationHelper hibernate.hbm2ddl.auto в режим validate
5. Запустить в тестовом классе CarEntitySetProcessorTest метод shouldProcessInParallel
Ориентировочное время работы: 10 минут
6. По окончанию работ проверить данные в таблице. Ожидаемый результат представлен на скриншоте в папке docs 2019-07-03 17_26_29-H2 Console.png

## TODO  план
1. Облегчить запуск. Например полность перейти в H2 in memory вариант
2. Добавить выгрузку результатов например в json
3. Сделать класс запускалку для прогона всего цикла
4. В сканирование добавить опцию "сканировать бренд", например Mazda
5. Посмотреть что можно оптимизировать в настройках браузера, например выключить картинки. сейчас 4 потока загружают ПК очень сильно
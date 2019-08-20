# Домашнее задания #16, #17: Параллельное выполнение тестов
-[X] 16: Постройте небольшой грид.
-[X] 17: Научитесь использовать облачный грид.

+ Запустить несколько тестов в каком-нибудь облачном сервисе на выбор:

+ [saucelabs](https://saucelabs.com)
- https://www.browserstack.com
- https://www.gridlastic.com
- https://testingbot.com

## Подробности реализация
Тесты для запуска взял из HW06 (про yandex market).

## Создание инфраструктуры запуска
### Часть 16_1:
На локальной машине Grid запускается командами:
+ Server:
```java -jar selenium-server-standalone-3.141.59.jar -role hub```
+ Nodes:
```java -jar selenium-server-standalone-3.141.59.jar -role node -hub http://localhost:4444/grid/register```

Пример запуска был в ходе занятия [screenshot](.\docs\grid_1\2019-08-09 21_06_11-Zoom.png).      
 
### Часть 16_2: Docker
Старту Grid через [Docker compose](.\docs\docker-compose.yml).

После запуска появится Grid c 5 возможными экземлярами Chrome & Firefox. Тесты запускаю в "N" потоков на Grid.
В работающем режими видно, что "N" ноды используются [screenshot](.\docs\grid_2_docker\2019-08-10 12_41_09-Grid Console.png).
В начальном варианте пробывал 4 потока. Команда запуска:

```mvn verify -DremoteDriver=true -DgridURL=http://192.168.99.100:4444/wd/hub -Dbrowser=chrome -Dthreads=4```


## Часть 17_1: Облачный Grid [Sauce Lab](https://saucelabs.com). 

-[X] Зарегистрировал учетную запись на сайте.
-[X] Добавил поддержку Sauce сайта в фреймворк.
-[X] Запустил тесты. По их окончанию просмотрел отчет на [сайте](.\docs\grid_3_sauce\2019-08-10 13_57_19-Sauce.png). 

Команда запуска:

```
mvn verify -DremoteDriver=true -DremoteType=sauce -DSAUCE_USERNAME=${userName} -DSAUCE_ACCESS_KEY=#{Key}
```

## Часть 20: Добавление Selenoid
```
mvn verify -DremoteDriver=true -DremoteType=selenoid -DgridURL=http://192.168.99.100:4444/wd/hub -Dthreads=4
```

## Дополнения
1. Добавил [Allure](.\docs\2019-08-10 16_39_22_Allure.png):

```mvn allure:serve```

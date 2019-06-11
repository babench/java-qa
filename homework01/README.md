# Домашнее задание #01
##Создать maven-проект

Создать новый maven проект для автоматизации тестирования

- [X]  Откройте IDE
- [X]  Создайте новый проект (maven)
- [X]  Настройте для проекта файл .gitignore (https://www.gitignore.io/)
- [X]  В файле pom.xml укажите зависимости для
- [X]     Selenium Java (https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java)
- [X]     WebDriverManager (https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager)
- [X]     jUnit (https://mvnrepository.com/artifact/junit/junit/4.12)
- [X]  Создайте тест, который:
- [X]  с помощью WebDriverManager, настраивает ChromeDriver
- [X]  открывает в браузере Chrome страницу https://otus.ru/
- [X]  Подключить и настроить log4j.

## Задание со звездочкой (*)
Реализовать отображение результата прогона тестов в Grafana, используя listener (TestNG) или test rule (jUnit). 
Подглядывать можно сюда: https://habr.com/ru/company/otus/blog/452908/

## Критерии оценки:
 - [X] +1 балл - проект компилируется и собирается
 - [X] +1 балл -  в репозитории нет лишних файлов (.iml, директория idea и т.д.)
 - [X] +1 балл -  логи пишутся в консоль и файл
 - [X] +1 балл -  в результате выполнения теста, в браузере Chrome открылся сайт https://otus.ru/ 
 - [X] +3 балла - задание со звездочкой
 
 ## Docker
 docker run -p 8086:8086 influxdb
 curl -i -XPOST http://192.168.99.100:8086/query --data-urlencode "q=CREATE DATABASE selenium"
 
 docker run -d -p 3000:3000 --name=grafana -e "GF_INSTALL_PLUGINS=grafana-piechart-panel"  grafana/grafana
 
 Пример итогов в Grafana расположен в docs\Grafana_sample.png
 
## Подробности реализация

1. В pom.xml созданы профили {dev;uat;preprod;prod}. По умолчанию активен dev.
2. Фабрика логирования возращает каким способом записывать протокол тестирования.
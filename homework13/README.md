# Домашнее задание #13: Реализовать BDD подход

##Цель: Уметь внедрять BDD-подход в существующий проект и менять архитектуру под BDD подход.

##TODO:
- [X] Реализовать 10 BDD-сценариев для сайта https://www.gosuslugi.ru/
- [X] Использовать Page Object и Cucumber
- [X] Уделить внимание удобству изменения Step Implementations и возможности переиспользовать шаги написанные на Gherkin

## Подробности реализации

1. Базовые страница BasePage. От нее наследуются страницы приложения
2. Аннотации:
Name - для описания страниц и элементов страницы
Optional - доп. атрибут, если элемента может не быть сразу на странице
При старте приложение определяет по Аннотации Name и наследникам BasePage доступные страницы
3. Features 
index.feature
login.feature
raiting.feature
На каждую фичу отдельные runner.

Фреймворк избыточен для задания, но хотелось побольше сделать, чтобы лучше сформировать представление о разработке в стиле BDD + Selenium.  

## Runner

```mvn test -Dtest="ru.otus.zaikin.gosuslugi.CucumberTestRunnerIndexFeature"```

```mvn test -Dtest="ru.otus.zaikin.gosuslugi.CucumberTestRunnerRaitingFeature"```

```mvn test -Dtest="ru.otus.zaikin.gosuslugi.CucumberTestRunnerLoginFeature" -Dme.login=<my_phone> -Dme.password=<my_password>```

```mvn test -Dcucumber.options="--tags @index,@raiting" -Dtest="ru.otus.zaikin.gosuslugi.CucumberTestRunner"``` 

Запустить все тесты через mvn test:

```mvn test -Dtest="ru.otus.zaikin.gosuslugi.**" -Dme.login=<my_phone> -Dme.password=<my_password>```


P.S. Логин и Пароль нужны зайти в личную страничку и проверить, что там есть INN

### Фрагмент лога
 Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 45.349 s - in TestSuite


Feature: Login to Site

  Scenario: Wrong User Name format
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Вход в личный кабинет"
    Then page "Вход в личный кабинет" has been loaded
    And element named "userName" is clickable
    When into the field named "userName" has been typed value "test"
    And  into the field named "userPassword" has been typed value "password"
    And pressed button named "loginButton"
    Then field named "field error" contains inner text "Введите мобильный телефон или почту"

  Scenario: Personal page shows INN number
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Вход в личный кабинет"
    Then page "Вход в личный кабинет" has been loaded
    When  user "me" entered login and password
    And opened page "Личная страница" by link "https://lk.gosuslugi.ru/info"
    Then field named "ИНН" contains 12 symbols
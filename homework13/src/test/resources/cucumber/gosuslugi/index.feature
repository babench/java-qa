Feature: Site availability

  Scenario: Penalty GIBDD page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Проверка штрафов"
    Then page "Автомобильные и дорожные штрафы" has been loaded
    And element named "Заголовок" contains inner text "Автомобильные и дорожные штрафы"

  Scenario: Foreign Passport page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Получение загранпаспорта"
    Then page "Заграничный паспорт" has been loaded
    And element named "Заголовок" contains inner text "Заграничный паспорт"

  Scenario: To Doctor page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Запись к врачу"
    Then page "Запись на прием к врачу" has been loaded
    And element named "Заголовок" contains inner text "Запись на прием к врачу"

  Scenario: Raiting page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When opened page "Рейтинг электронных услуг" by link "https://www.gosuslugi.ru/rating"
    Then field named "Page Title" contains inner text "Народный рейтинг электронных госуслуг"

  Scenario: New Baby page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Родители и дети"
    Then page "Рождение ребёнка" has been loaded
    And element named "Заголовок" contains inner text "Рождение ребёнка"

  Scenario: Criminal records page is available
    Given opened page "Главная" by link "https://www.gosuslugi.ru"
    When pressed block named "Справка об отсутствии судимости"
    Then page "Справка об отсутствии судимости" has been loaded
    And element named "Заголовок" contains inner text "Справка об отсутствии судимости"
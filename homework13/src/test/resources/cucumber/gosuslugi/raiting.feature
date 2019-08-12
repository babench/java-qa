@rating
Feature: Rating of services

  Scenario: Top-1 in header and table the same
    Given opened page "Рейтинг электронных услуг" by link "https://www.gosuslugi.ru/rating"
    When value from the element named "Top-1-in-header" has been saved to the variable named "top1"
    Then value of the element named "Top-1-in-list" is equal to variable named "top1"

  Scenario: Top-1 score higher than Top-2
    Given opened page "Рейтинг электронных услуг" by link "https://www.gosuslugi.ru/rating"
    When value from the element named "Top-1-score" has been saved to the variable named "top-1-score"
    And  value from the element named "Top-2-score" has been saved to the variable named "top-2-score"
    Then variable named "top-1-score" is not equal to variable named "top-2-score"
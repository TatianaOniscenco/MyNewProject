Feature: APIs

  @Positive
  Scenario: Get all products list
    Given System is up and running
    When User does GET call to "https://automationexercise.com/api/productsList" endpoint
    Then Response code is 200
    And Response contains a list of products

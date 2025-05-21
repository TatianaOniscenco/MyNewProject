Feature: APIs

  Background:
    Given System is up and running

  @Positive
  Scenario: Get all products list
    When User does GET call to "/api/productsList" endpoint
    Then Response code is 200
    And Response contains a list of products

  @Negative
  Scenario: Post to all products list
    When User does POST call to "/api/productsList" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported"

  @Positive
  Scenario: Get all products list
    When User does GET call to "/api/brandsList" endpoint
    Then Response code is 200
    And Response contains a list of brands

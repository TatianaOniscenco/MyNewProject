@API
Feature: APIs

  Background:
    Given System is up and running

  @Positive @API1
  Scenario: Get all products list
    When User does GET call to "/api/productsList" endpoint
    Then Response code is 200
    And Response contains a list of products

  @Negative @API2
  Scenario: Post to all products list
    When User does POST call to "/api/productsList" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported"

  @Positive @API3
  Scenario: Get all brands list
    When User does GET call to "/api/brandsList" endpoint
    Then Response code is 200
    And Response contains a list of brands

  @Negative @API4
  Scenario: Put to all brands list
    When User does PUT call to "/api/brandsList" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported"

  @Positive @API5
  Scenario Outline: Post to search product
    When User searches for "<search_product>" via POST to "api/searchProduct"
    Then Response code is 200
    And Response contains searched products list
    Examples:
    | search_product |
    | top            |
    | tshirt         |
    | jean           |
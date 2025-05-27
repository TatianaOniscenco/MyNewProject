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

  @Negative @API6
  Scenario: Post to search product without search_product parameter
    When User does POST call to "api/searchProduct" endpoint
    Then Response code is 200
    And Response message is "Bad request, search_product parameter is missing in POST request."

  @Positive @API7
  Scenario: Post to verify login with valid details
    When verify "tatiana.oniscenco@endava.com" and "password" via POST to "api/verifyLogin"
    Then Response code is 200
    And Response message is "User exists!"

  @Negative @API10
   Scenario: Post to verify login with invalid details
    When verify "boom@endava.com" and "password123" via POST to "api/verifyLogin"
    Then Response code is 200
    And Response message is "User not found!"

  @Negative @API8
  Scenario: Post to verify login without email parameter
    When verify "password" via POST to "api/verifyLogin"
    Then Response code is 200
    And Response message is "Bad request, email or password parameter is missing in POST request."

  @Negative @API9
  Scenario: Delete to verify login
    When user does DELETE cal to "api/verifyLogin" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported."
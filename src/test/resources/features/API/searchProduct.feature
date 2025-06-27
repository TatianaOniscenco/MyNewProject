@API
Feature: API: searchProduct endpoint

  Background:
    Given System is up and running

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
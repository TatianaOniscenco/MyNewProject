@API
Feature: API: searchProduct endpoint

  @Positive @API5
  Scenario Outline: Post to search product
    When User searches for "<search_product>" via POST to SEARCH_PRODUCT endpoint
    Then Response code is 200
    And Response contains searched products list
    Examples:
      | search_product |
      | top            |
      | tshirt         |
      | jean           |

  @Negative @API6
  Scenario: Post to search product without search_product parameter
    When User sends "POST" request to "SEARCH_PRODUCT" endpoint
    Then Response code is 200
    And Response message is "Bad request, search_product parameter is missing in POST request."
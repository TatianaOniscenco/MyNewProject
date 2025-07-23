@API
Feature: API: productsList endpoint

  @Positive @API1
  Scenario: Get all products list
    When User sends "GET" request to "PRODUCTS_LIST" endpoint
    Then Response code is 200
    And Response contains a list of products

  @Negative @API2
  Scenario: Post to all products list
    When User sends "POST" request to "PRODUCTS_LIST" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported"
@API
Feature: API: brandsList endpoint

  @Positive @API3
  Scenario: Get all brands list
    When User sends "GET" request to "BRANDS_LIST" endpoint
    Then Response code is 200
    And Response contains a list of brands

  @Negative @API4
  Scenario: Put to all brands list
    When User sends "PUT" request to "BRANDS_LIST" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported"
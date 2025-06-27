@API
Feature: API: brandsList endpoint

  Background:
    Given System is up and running

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
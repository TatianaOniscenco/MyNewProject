@API
Feature: API: verifyLogin endpoint

  Background:
    Given System is up and running

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
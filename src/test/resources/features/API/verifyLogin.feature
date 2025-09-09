@API
Feature: API: verifyLogin endpoint

  # This feature file tests the verifyLogin endpoint for different scenarios
  # including positive and negative cases.

  @Positive @API7
  Scenario: Post to verify login with valid details
    When verify login with "valid email" and "valid password"
    Then Response code is 200
    And Response message is "User exists!"

  @Negative @API10
   Scenario: Post to verify login with invalid details
    When verify login with "invalid email" and "invalid password"
    Then Response code is 200
    And Response message is "User not found!??"

  @Negative @API8
  Scenario: Post to verify login without email parameter
    When verify login with only "valid password"
    Then Response code is 500
    And Response message is "Bad request, email or password parameter is missing in POST request."

  @Negative @API9
  Scenario: Delete to verify login
    When User sends "DELETE" request to "VERIFY_LOGIN" endpoint
    Then Response code is 200
    And Response message is "This request method is not supported."
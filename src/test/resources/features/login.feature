Feature: User login

  @Negative
  Scenario: Login with incorrect email and password
    Given Homepage is loaded
    And User navigates to login page
    When User inputs "user@invalid.com" and "123456R" credentials
    And User submits the login form
    Then System displays the "Your email or password is incorrect!" message


  @Positive
  Scenario: Login with correct email and password
    Given Homepage is loaded
    And User navigates to login page
    When User inputs "tatiana.oniscenco@endava.com" and "password" credentials
    And User submits the login form
    And User is redirected to homepage
    Then System displays "Logged in as Tatiana Oniscenco"


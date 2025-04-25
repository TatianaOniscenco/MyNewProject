Feature: User login

  Scenario: Login with incorrect email and password
    Given User is on the login page
    When User enters invalid "user@invalid.com" and "123456R" credentials
    And User submits the login form
    Then System displays the "Your email or password is incorrect!" message
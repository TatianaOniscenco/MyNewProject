Feature: User authentication

  Scenario: Successful user registration
    Given User is on the signup page
    When User enters valid user information
    And User submits the registration form
    Then System displays the "Account Created!" message

  Scenario: Login with incorrect email and password
    Given User is on the login page
    When User enters incorrect credentials
    And User submits the login form
    Then System displays the "Your email or pasword is incorrect!" message
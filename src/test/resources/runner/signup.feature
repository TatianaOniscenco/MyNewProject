Feature: User signup

  Scenario: Successful user signup
    Given User is on the login page
    When User enters valid user information
    And User submits the signup form
    Then System displays the "Account Created!" message


Feature: User signup

  @Positive
  Scenario: Successful user signup
    Given Homepage is loaded
    And User navigates to login page
    When User enters valid user information
    And User submits the signup form
    And User is redirected to Account Created page
    Then System displays the "Account Created!" message


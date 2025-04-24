Feature: User registration

  Scenario: Successful user registration
    Given User is on the signup page
    When User enters valid user information
    And User submits the registration form
    Then System displays the "Account Created!" message


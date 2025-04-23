Feature: User authentication

  Scenario: Successful user registration
    Given I am on the signup page
    When I enter valid user information
    And I submit the registration form
    Then I should see the "Account Created!" message

  Scenario: Login with incorrect email and password
    Given I am on the login page
    When I enter incorrect credentials
    And I submit the login form
    Then I should see the "Your email or password is incorrect!" message
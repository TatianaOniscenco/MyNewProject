Feature: User signup

  @Positive
  Scenario: Successful user signup
    Given Homepage is loaded
    And User navigates to login page
    And User inputs new valid credentials in New User Signup form
    And User clicks on Signup button
    And user is redirected to Signup page
    When User enters valid account information
    And User submits the signup form clicking on Create Account button
    And User is redirected to Account Created page
    And System displays the "Account Created!" message
    And User clicks Continue button
    And User is redirected to homepage
    And System displays "Logged in as" up in the header
    When User clicks Delete Account button
    Then System displays the "ACCOUNT DELETED!" message

  @Negative
  Scenario: Register user with existing email
    Given Homepage is loaded
    And User navigates to login page
    When User inputs existing email "tatiana.oniscenco@endava.com" in New User Signup form
    And User clicks on Signup button
    Then System displays the "Email Address already exist!" message


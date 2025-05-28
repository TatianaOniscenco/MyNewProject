Feature: User signup
Background:
  Given Homepage is loaded
  And User navigates to login page

  @Positive @Only
  Scenario: Successful user signup
    And User inputs new valid credentials in New User Signup form
    And User clicks on Signup button
    And user is redirected to Signup page
    When User enters valid account information
    And User submits the signup form clicking on Create Account button
    And User is redirected to Account Created page
    And System displays the "Account Created!" message
    And User clicks Continue button
    And User is redirected to homepage
    And System displays username up in the header
    When User clicks Delete Account button
    Then System displays the "ACCOUNT DELETED!" message

  @Negative
  Scenario: Register user with existing email
    When User inputs existing email "tatiana.oniscenco@endava.com" in New User Signup form
    And User clicks on Signup button
    Then System displays the "Email Address already exist!" message

  @Positive @Only
  Scenario: Successful user login after signup
    And User inputs new valid credentials in New User Signup form
    And User clicks on Signup button
    And user is redirected to Signup page
    When User enters valid account information
    And User submits the signup form clicking on Create Account button
    And User is redirected to Account Created page
    And System displays the "Account Created!" message
    And User clicks Continue button
    And User is redirected to homepage
    When System displays username up in the header
    Then User clicks Logout button
    And User inputs recent valid credentials to login
    And User submits the login form
    And User is redirected to homepage
    Then System displays username up in the header



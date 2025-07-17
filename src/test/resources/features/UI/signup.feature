@UI
Feature: UI: User signup

Background: User is on login page
  Given Homepage is loaded
  And User navigates to login page

  @Positive
  Scenario: Successful user signup
    And User inputs new valid credentials in New User Signup form
    When User clicks on Signup button
    Then user is redirected to Signup page
    And User enters valid account information
    When User submits the signup form clicking on Create Account button
    Then User is redirected to Account Created page
    And System displays the "Account Created!" message confirming account creation
    When User clicks Continue button
    Then User is redirected to homepage
    And System displays username up in the header
    When User clicks Delete Account button
    Then System displays the "Account Deleted!" message confirming delete

  @Negative
  Scenario: Register user with existing email
    And User inputs existing email "tatiana.oniscenco@endava.com" in New User Signup form
    When User clicks on Signup button
    Then System displays the "Email Address already exist!" message for existing user

  @Positive
  Scenario: Successful user login after signup
    And User inputs new valid credentials in New User Signup form
    When User clicks on Signup button
    Then user is redirected to Signup page
    And User enters valid account information
    When User submits the signup form clicking on Create Account button
    Then User is redirected to Account Created page
    And System displays the "Account Created!" message confirming account creation
    When User clicks Continue button
    Then User is redirected to homepage
    And System displays username up in the header
    And User clicks Logout button
    And User inputs recent valid credentials to login
    When User submits the login form
    Then User is redirected to homepage
    And System displays username up in the header



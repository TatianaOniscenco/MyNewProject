@UI
Feature: UI: User login

Background: User is on login page
  Given Homepage is loaded
  And User navigates to login page

  @Negative @Only
  Scenario: Login with incorrect email and password
    When User inputs "invalid email" and "invalid password" credentials
    And User submits the login form
    Then System displays the "Your email or password is incorrect!" message

  @Positive
  Scenario: Login with correct email and password
    When User inputs "valid email" and "valid password" credentials
    And User submits the login form
    And User is redirected to homepage
    Then System displays "Logged in as Tatiana Oniscenco" up in the header


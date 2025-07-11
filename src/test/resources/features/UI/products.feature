@UI
Feature: UI: Add products to the cart

  @Positive
  Scenario Outline: Add one product to the cart and verify cart details
    Given User is on Products page
    When Adds "<productName>" product to the cart
    And User views the cart clicking on the button in the modal
    Then User is redirected to Cart Page
    And Selected "<productName>" is visible in the cart
    And Cart displays correct price "<productPrice>"
    And Cart displays calculated total per product correctly
    Examples:
      | productName | productPrice |
      | Blue Top    | Rs. 500          |
      | Men Tshirt  | Rs. 400          |

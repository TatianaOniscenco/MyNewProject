@UI
Feature: UI: Add products to the cart

  @Positive
  Scenario Outline: Add <productName> to the cart and verify cart details
    Given User is on Products page
    When Adds "<productName>" product to the cart
    And User views the cart clicking on the button in the modal
    Then User is redirected to Cart Page
    And Selected "<productName>" is visible in the cart
    And Cart displays correct price "<productPrice>"
    Examples:
      | productName | productPrice |
      | Blue Top    | Rs. 600      |
      | Men Tshirt  | Rs. 400      |

@UI
Feature: UI: Add products to the cart

  @Positive
  Scenario Outline: Add one product to the cart and verify cart details
    Given User is on Products page
    And adds "<productName>" product to the cart
    When user views the cart clicking on the button in the modal
    Then User is redirected to Cart Page
    Then selected "<productName>" is visible in the cart
    And cart displays correct price "<productPrice>" and quantity
    And cart displays calculated total per product correctly
    Examples:
      | productName | productPrice |
      | Blue Top    | 500          |
      | Men Tshirt  | 400          |

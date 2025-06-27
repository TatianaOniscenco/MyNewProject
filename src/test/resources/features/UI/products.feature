@UI
Feature: UI: Add products to the cart

  @Positive
  Scenario Outline: Add one product to the cart and verify cart details
    Given User is on Products page
    When adds "<productName>" product to the cart
    And user views the cart
    Then selected "<productName>" is visible in the cart
    And cart displays correct price "<productPrice>" and quantity
    And cart displays calculated total per product correctly
    Examples:
      | productName | productPrice |
      | Blue Top    | 500          |
      | Men Tshirt  | 400          |

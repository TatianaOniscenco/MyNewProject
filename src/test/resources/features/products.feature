Feature: Add products to the cart

  @Positive
  Scenario Outline: User adds multiple products to the cart and verifies cart details
    Given User is on Products page
    When adds "<productName>" product to the cart
    #And chooses to continue shopping
    #And adds "<productName>" product to the cart
    And user views the cart
    Then selected "<productName>" is visible in the cart
    And cart displays correct price "<productPrice>" and quantity
    And cart displays calculated total per product correctly
    Examples:
      | productName | productPrice |
      | Blue Top    | 500          |
      | Men Tshirt  | 400          |

Feature: Add products in cart

  Scenario: Add products to cart
    Given User is on Products page
    And hover over "first" product
    And click "Add to cart"
    And click "Continue shopping" button
    And hover over "second" product
    And click "Add to cart"
    When click "View Cart" button
    Then "first" and "second" products are added to cart
    And validate prices, quantity and total price
    
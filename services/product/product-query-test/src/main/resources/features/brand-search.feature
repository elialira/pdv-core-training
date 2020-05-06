Feature: Brand Search

  Scenario: Search Brand Successfully

    Given I have a 'activated' Brand with 'Xbox' name
    When I search the brand
    Then I should see the brand with 'activated' status and 'Xbox' name

  Scenario: Unbranded Search

    When I search the brand
    Then I should only see 'Brand was not found' message
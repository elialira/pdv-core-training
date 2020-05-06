Feature: Search All Brands

  Scenario: Search Brand Successfully

    Given I have a 'activated' Brand with 'Xbox' name
    When I search for all brand
    Then I should only see brand with 'activated' status and 'Xbox' name

  Scenario: Search Two Brand Successfully

    Given I have a 'activated' Brand with 'Xbox' name
    And I have a 'activated' Brand with 'LG' name
    When I search for all brand
    Then I should see '2' brands
    And I should see a brand with 'activated' status and 'Xbox' name
    And I should see a brand with 'activated' status and 'LG' name

  Scenario: Search Brand Successfully

    When I search for all brand
    Then I should see '0' brands
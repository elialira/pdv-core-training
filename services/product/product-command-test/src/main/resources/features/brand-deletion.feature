Feature: Brand Deletion

  Scenario: successful Deletion of the Brand

    Given I have a 'activated' Brand with 'Xbox' name
    When I delete the Brand
    And I should be notified with the brand deleted event

  Scenario: Trying to Delete a non-existent Brand

    When I delete the Brand
    Then I should only see 'Brand was not found' message
Feature: Brand Creation

  Scenario: Successful Band Creation

    When I save a 'activated' Brand with 'Xbox' name
    Then It should have successfully saved a brand
    And I should have a brand created with 'active' status and 'Xbox' name
    And I should be notified with the brand created event containing 'activated' status and 'Xbox' name

  Scenario: Brand Without Name

    When I save a 'activated' Brand with 'null' name
    Then I should only see 'Name cannot be empty or null' message

  Scenario: Brand Without Activated

    When I save a 'null' Brand with 'Xbox' name
    Then I should only see 'Activated cannot be null' message

  Scenario: Brand Without Activated and Name

    When I save a 'null' Brand with 'null' name
    Then I should see '2' messages
    And I should see 'Name cannot be empty or null' message
    And I should see 'Activated cannot be null' message
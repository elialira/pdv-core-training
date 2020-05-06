Feature: Brand Change

  Scenario: Successful Band Change

    Given I have a 'activated' Brand with 'Xbox' name
    When I change the Brand status to 'inactive' and the name to 'Playstation'
    And I should have a brand changed with 'inactive' status and 'Playstation' name
    And I should be notified with the brand changed event containing 'inactive' status and 'Playstation' name

  Scenario: Trying to Change a non-existent Brand

    When I change the Brand status to 'inactive' and the name to 'Playstation'
    Then I should only see 'Brand was not found' message

  Scenario: Brand Without Name

    Given I have a 'activated' Brand with 'Xbox' name
    When I change the Brand status to 'inactive' and the name to 'null'
    Then I should only see 'Name cannot be empty or null' message

  Scenario: Brand Without Activated

    Given I have a 'activated' Brand with 'Xbox' name
    When I change the Brand status to 'null' and the name to 'Playstation'
    Then I should only see 'Activated cannot be null' message

  Scenario: Brand Without Activated and Name

    Given I have a 'activated' Brand with 'Xbox' name
    When I change the Brand status to 'null' and the name to 'null'
    Then I should see '2' messages
    And I should see 'Name cannot be empty or null' message
    And I should see 'Activated cannot be null' message
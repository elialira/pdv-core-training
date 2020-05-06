Feature: My First Feature

  Scenario Outline: My First Scenario
    Given I have <test> cukes in my belly
    Then I print

    Examples:
    | test |
    | 1    |
    | 2    |


  Scenario: My Second Scenario
    Given I have 7 cukes in my bellies
    When I login with credentials
    | user1 | pass1 |
    | user2 | pass2 |
    Then I print

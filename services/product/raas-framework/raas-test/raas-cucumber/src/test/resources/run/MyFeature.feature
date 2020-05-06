Feature: My Second Feature

  Scenario: My First Scenario
    Given I have 10 cukes in my belly
    Then I print

  Scenario Outline: My Second Scenario

    Given I have <test> cukes in my belly
    Then I print

    Examples:
      | test |
      | 1    |
      | 2    |

  Scenario: My Third Scenario

    Given I have 7 cukes in my bellies
    When I login with credentials
      | user1 | pass1 |
      | user2 | pass2 |
    Then I print
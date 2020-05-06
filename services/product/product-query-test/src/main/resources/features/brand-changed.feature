Feature: Brand Changed

  Scenario: Successfully Received Brand Changed Event

    Given I have a 'activated' Brand with 'Xbox' name
    When I notify 'disabled' Brand Changed with 'LG' name
    Then I should have a brand with 'disabled' status and 'LG' name
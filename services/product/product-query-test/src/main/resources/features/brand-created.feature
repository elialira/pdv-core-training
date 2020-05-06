Feature: Brand Created

  Scenario: Successfully Received Brand Created Event

    When I notify 'activated' Brand Created with 'XBox' name
    Then I should have a brand with 'activated' status and 'XBox' name
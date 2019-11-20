Feature: Home Page Search Tests

  Scenario: User can do a search from the home page
    Given I search "Selenium" in the search input of the home page
    When I press the search button in the home page
    Then the links are displayed on the results page
Feature: Recruitment management
  Sample scenario of recruitment management menu in OrangeHRM demo app

	Background: User is logged in
		Given User navigate to HRM website
		When User do login as Admin
		Then Application will shows homescreen with welcome message
		
  Scenario: Verify user able to open existing vacancy detail
    Given User navigate to "Recruitment/Vacancy" menu
    When User search for existing Vacancy on Role "Senior QA Lead"
    Then System will shows all existing vacancy on "Senior QA Lead"
    	And User can open the vacancy detail
		
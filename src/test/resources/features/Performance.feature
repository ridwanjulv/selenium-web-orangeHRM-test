Feature: Performance management
  Sample scenario of performance management menu in OrangeHRM demo app

	Background: User is logged in
		Given User navigate to HRM website
		When User do login as Admin
		Then Application will shows homescreen with welcome message
  
  Scenario: Verify user able to cancel Adding new KPI
    Given User navigate to "Performance/Configure/KPI" menu
    	And User click Add new button
    	And User populate KPI data
    When User cancel the Add KPI
    Then System will goes back to KPI management landing page

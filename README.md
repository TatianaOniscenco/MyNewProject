MyNewProject - Automated Testing Framework (ATF) to test https://automationexercise.com


TECH STACK

Java 24
Playwright (for browser automation)
Cucumber (for BDD style tests)
Cucumber JUnit 5 Platform Engine (for running BDD tests)
RestAssured (API testing)
DataFaker (test data)
SLF4J + Logback (logging)
Maven (build tool)

SETUP

Install Java 24
Install Maven
Install Playwright browsers:
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

CONFIGURATION

Edit config.properties:
base.url=https://automationexercise.com
browser=chromium
headless=true

RUN TESTS
To run all tests:
Run CucumberTestRunner class

To run only specific tagged tests:
In junit-platform.properties specify needed tags to run - cucumber.filter.tags=@Only

@Positive, @Negative - test result types
@UI, @API - test layer
@Only - for focused runs

NOTES
Page Object Model is used for maintainable UI automation.
Configurable browser and headless mode.
Logs and a screenshot saved on every test: Passed or Failed, for evidence.

API tests supported with RestAssured.

AUTHOR
Tatiana Oniscenco
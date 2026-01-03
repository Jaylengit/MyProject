Feature: SauceDemo login test

Scenario Outline: Login with multiple users

Given I am on the SauceDemo login page
When I login with "<username>" and "<password>"
Then the login result should be "<expected>"

Examples:
  | username         | password       | expected  |
  | standard_user    | secret_sauce   | success   |
  | locked_out_user  | secret_sauce   | locked    |
  | invalid_user     | wrong_pass     | invalid   |  
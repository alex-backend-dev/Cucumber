@sad
Feature: Delete Board Validation
  As a Trello API user
  I want to delete my board safely
  So that I want to delete board endpoint to allow delete only with valid request

  Scenario Outline: Check Delete Board With Invalid Id
    Given a request with authorization
    And the request has path params:
      | name | value      |
      | id   | <id_value> |
    When the 'DELETE' request is sent to 'DELETE_A_BOARD' endpoint
    Then the response status code is <status_code>
    And the response body is equal to '<error_message>'

    Examples:
      | id_value                 | status_code | error_message                         |
      | invalid                  | 400         | invalid id                            |
      | 66b4f7b6289fb12a6808acf1 | 404         | The requested resource was not found. |

  Scenario Outline: Check Delete Board With Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has path params:
      | name | value                    |
      | id   | 66b4f7b6289fb12a6808acfb |
    When the 'DELETE' request is sent to 'DELETE_A_BOARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'

    Examples:
      | key              | token              | error_message                |
      | empty_value      | empty_value        | {"message":"missing scopes"} |
      | current_user_key | empty_value        | {"message":"missing scopes"} |
      | empty_value      | current_user_token | invalid key                  |
      | another_user_key | another_user_token | invalid key                  |

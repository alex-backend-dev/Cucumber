Feature: Delete Card Validation
  As a Trello API user
  I want to delete my card safely
  So that I want to delete card endpoint to allow delete only with valid request

  Scenario Outline: Check Delete Card With Invalid Id
    Given a request with authorization
    And the request has path params:
      | name | value      |
      | id   | <id_value> |
    When the 'DELETE' request is sent to 'DELETE_A_CARD' endpoint
    Then the response status code is <status_code>
    And the response body is equal to '<error_message>'

    Examples:
      | id_value                 | status_code | error_message                         |
      | invalid                  | 400         | invalid id                            |
      | 66b4f7b6289fb12a6808acf1 | 404         | The requested resource was not found. |

  Scenario Outline: Check Delete Card With Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has path params:
      | name | value                    |
      | id   | 66b4f87cadb20f9d0ef8dca4 |
    When the 'DELETE' request is sent to 'DELETE_A_CARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'

    Examples:
      | key                              | token                                                                        | error_message                |
      |                                  |                                                                              | {"message":"missing scopes"} |
      | ba54293d6062bc75d1ea8ce515e336ba |                                                                              | {"message":"missing scopes"} |
      |                                  | ATTAb5cbc51a7ad518e1a7eb7e3d7d8389308154125e6732e3c17f107834835be9784DB52629 | invalid key                  |
      | 8b32218e6887516d17c84253faf967b6 | 492343b8106e7df3ebb7f01e219cbf32827c852a5f9e2b8f9ca296b1cc604955             | invalid key                  |

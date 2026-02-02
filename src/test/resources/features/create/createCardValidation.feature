@sad
Feature: Create Card Validation
  As a Trello API user
  I want to create my card safely
  So that I want to create card endpoint to allow create only with valid request

  Scenario Outline: Check Create Card With Invalid Name
    Given a request with authorization
    And the request has body params:
      | name   | <name_value>   |
      | idList | <idList_value> |
    And the request has headers:
      | Content-Type | application/json |
    When the 'POST' request is sent to 'CREATE_A_CARD' endpoint
    Then the response status code is 400
    And the response body is equal to 'invalid value for idList'

    Examples:
      | name_value | idList_value              |
      | 1.23       | 66b4f7efae08f2099d4e300ee |
      | New card   |                           |
      | New card   | invalid                   |


  Scenario Outline: Check Create Card With Invalid Auth
    Given a request without authorization
    And the request has query params:
      | key   | <key>   |
      | token | <token> |
    And the request has body params:
      | idList | 66b4f7efae08f2099d4e300f |
      | name   | new card                 |
    And the request has headers:
      | Content-Type | application/json |
    When the 'POST' request is sent to 'CREATE_A_CARD' endpoint
    Then the response status code is 401
    And the response body is equal to '<error_message>'

    Examples:
      | key                              | token                                                                        | error_message                |
      | empty_value                      | empty_value                                                                  | {"message":"missing scopes"} |
      | ba54293d6062bc75d1ea8ce515e336ba | empty_value                                                                  | {"message":"missing scopes"} |
      | empty_value                      | ATTAb5cbc51a7ad518e1a7eb7e3d7d8389308154125e6732e3c17f107834835be9784DB52629 | invalid key                  |

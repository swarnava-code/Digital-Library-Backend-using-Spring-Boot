
# Setup API Key

1. Run the app, it will create db structure.
2. Setup API Key for the application in db.
```roomsql
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (1, 'e1a2b3c4-d5f6-7g8h-9i0j-k1l2m3n4o5p6', 'ADMIN');
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (2, 'e1a2b3c4-d5f6-7g8h-9i0j-k1l7m3n4u5p6', 'DEVOPS TEAM');
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (3, 'e1a2b3c4-d5f6-7h8h-9i0j-k1l2m3n4i5p6', 'EC TEAM');
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (4, 'e1a2c3c4-d5f6-7h8h-9i0j-k1l2m3n4o5p6', 'UAM TEAM');
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (5, 'e1a2b3c4-d5f6-7g8h-9i0j-k1l2m3n4o666', 'CLIENT-UI-ANDROID');
INSERT INTO api_key_entity (id, key_value, user_type) VALUES (6, 'e1a2b3c4-d5f6-7g8h-9i0j-k1l2m3n4o777', 'CLIENT-UI-iOS');
```
3. Use Postman or Terminal to hit the API with the API Key.
```shell
swarnavachakraborty@Swarnavas-MacBook-Air ~ % curl --location 'http://localhost:8081/author/c4a4580f-6589-4003-9e2c-cd197c3052c5' \
--header 'X-API-KEY: e1a2b3c4-d5f6-7g8h-9i0j-dev0be0admin'  
{"id":"c4a4580f-6589-4003-9e2c-cd197c3052c5","name":"Eric Thiel","email":"Franco.Welch78@gmail.com","age":15,"country":"Tuvalu"}%                                                                                                                 
swarnavachakraborty@Swarnavas-MacBook-Air ~ % 
```
4. If you hit the API without the API Key, you will get the following response.
```shell
401 Unauthorized
{
    "timestamp": "2024-12-25T23:16:13.339293",
    "status": 401,
    "message": "API Key not found!",
    "path": "/author/c4a4580f-6589-4003-9e2c-cd197c3052c5"
}
```
or
```shell
401 Unauthorized
{
    "timestamp": "2024-12-25T23:16:35.779990",
    "status": 401,
    "message": "Missing API Key",
    "path": "/author/c4a4580f-6589-4003-9e2c-cd197c3052c5"
}
```

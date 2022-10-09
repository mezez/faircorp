AUTHOR: **CHIMEZIRIM VICTOR EKEMAM**

Project: API based application for the management of a smart building.

The implementation manages buildings with respect to its rooms, windows and heaters.

**IMPLEMENTATION**

AUTHENTICATION
- The application is protected by BASIC AUTHENTICATION
- System is implemented with 2 in memory users, admin and user
- The credentials for the two users are 

admin
- username: **admin**
- password: **password**


user
- username: **user**
- password: **password**


- **The [admin] user has full access to all parts of the system. The [user] user only has access to GET requests on the /api routes**

**APIS** 


USERS: /admin-api/users
- Has only one endpoint; getLoggedInUsername which returns the username of the currently logged-in user. 
- Only the admin can access this endpoint

The rest of the endpoints follow the /api/** url pattern.

- The regular user only has rights to the get endpoints. The rest requires admin credentials
- All modules have the traditional CRUD ENDPOINTS
- Create and Update operations use the same endpoints and the system detects whether the operation is a create or update based on the presence or absence of an ID for the record
- In addition to the traditional CRUD endpoints;
- The heater and window APIs have the switch endpoints for toggling the state of a heater/window
- The room API has switchWindowsStatus and switchHeatersStatus endpoints for toggling the status of heaters or windows in a room
- The buidling API the switchAllHeatersStatus for turning all heaters in a building either ON or OFF
- The buidling API the switchAllWindowsStatus for OPENING or CLOSING all windows in a building
- It also has a findByName endpoint with returns buildings that matches the name used in querying

**TEST**

Test have been written to cover the APIs and Data Access objects of the Application
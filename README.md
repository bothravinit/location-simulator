How to build - ./gradlew clean build
How to run - java -jar build/libs/location-simulator-1.0-RELEASE.jar


Sample testing -
1. Route found - using some HTTP client (or direct browser) hit -
http://localhost:8080/simulate?origin=HSR%20Layout,%20Bangalore&destination=whitefield,%20bangalore&speed_factor=5

How to track (actual location simulation using gmaps) -
open resources/html/order_tracker.html in browser
put any dummy order id and click 'track this order'
map should start showing current location.

2. Route not found - http://localhost:8080/simulate?origin=New%20York&destination=whitefield,%20bangalore&speed_factor=5
{
    "code": 400,
    "message": "No directions found from New York to whitefield, bangalore"
}


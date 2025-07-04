# orders-micro-service

## Routes

* `GET http://localhost:8081/orders` - Retrieve a list of orders
* `POST http://localhost:8081/orders` - Create a new order
* `PUT http://localhost:8081/orders/{id}` - Update an existing order
* `DELETE http://localhost:8081/orders/{id}` - Delete an order
* `GET http://localhost:8081/orders/{id}` - Retrieve an order by ID

## Cli with makefile

* make dbstart (if already launched in other micro service don't to do it)
* make create_db (create the database if don't exist)
* make start_service (build project with dependencies and run with hot releaod mandatory to already have java and maven)

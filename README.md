restaurant-app

===

In order to change the database credentials, make sure to connect to a mysql server. In order to do that you can use the following docker command:

```

docker run --name mysql-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=restaurant -e MYSQL_USER=app -e MYSQL_PASSWORD=app -p 3306:3306 -d mysql:8.0

```



After starting the container, navigate to '/restaurant-app/backend' folder, create a .env file there and specify the envirovmental variables as follows:

```

DATASOURCE_URL=jdbc:mysql://localhost:3306/restaurant

DATASOURCE_USERNAME=app

DATASOURCE_PASSWORD=app

```



Finally, your database connection should be set and ready.




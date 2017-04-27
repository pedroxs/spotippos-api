# Spotippos API

API to create and search properties on Spotippos mystical world.
Try it [here](https://pedroxs-spotippos-api.herokuapp.com/swagger-ui.html#!/property-resource/searchPropertyUsingGET)!

## Development

Run the following command in a terminal to start a local server running the app.

    ./mvnw

## Building for production

To package the application for production, run:

    ./mvnw clean package

This will generate a jar file.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

To launch the application's tests, run:

    ./mvnw clean test

### Other tests

Performance tests are run by [Gatling][] and written in Scala. 
They're located in `src/test/gatling` and can be run with:

    ./mvnw gatling:execute

## Using Docker to simplify development (optional)

You can use Docker to improve your development experience. 
A number of docker-compose configuration are available in the `src/main/docker` 
folder to launch required third party services.
For example, to start a mongodb database in a docker container, run:

    docker-compose -f src/main/docker/mongodb.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mongodb.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d


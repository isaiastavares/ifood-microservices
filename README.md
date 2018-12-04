# Ifood Microservices

# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is between 15 and 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this). Once done, share your code with us.

## Overview

The architecture is composed by four services:

- `ifood-config-server`: All of our system configuration files are in the repository `https://github.com/isaiastavares/ifood-configs` and **iFood Config Server** will be responsible for reading the information in the repository and providing it to applications through HTTP requests.
- `ifood-eureka-server`: Service Discovery Server created with **Eureka**
- `ifood-zuul-server`: API Gateway created with **Zuul** that uses the `ifood-eureka-service` to send the requests to the services. It uses **Ribbon** as Load Balancer
- `ifood-suggestion-server`: Simple REST service created with **Spring Boot** to suggest tracks based on location.

## How to use

To test this architecture you will need to have: **JDK 8+**, **Docker** and **Maven** installed

- Clone this repo and enter it

- Run the `start.sh` script, it will use **Maven** to build the `.jar` file for each service and then use Docker to build the containers with the `.jar` files

In the default configuration you will have:

- **iFood Config Server** running on port `9090`
- **iFood Eureka Server** running on port `9091`, access `http://localhost:9091` to see the dashboard
- **iFood Zuul Server** running on port `9092`
- **iFood Suggestion Server** running on port: `9093`, you will send the requests to this service

After running the containers, head to `http://localhost:9091` to make sure that the service (**iFood Suggestion Server**) are registered in the **iFood Eureka Server**, when they're all registered you can test the application using swagger through the following link: `http://localhost:9093/swagger-ui.html`

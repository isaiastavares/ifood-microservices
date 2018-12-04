#!/bin/bash

echo iFood Config Server: BUILDING
cd ifood-config-server
mvn clean install -U
cd ..
clear

echo iFood Config Server: BUILD DONE
echo iFood Eureka Server: BUILDING
cd ifood-eureka-server
mvn clean install -U
cd ..
clear

echo iFood Config Server: BUILD DONE
echo iFood Eureka Server: BUILD DONE
echo iFood Zuul Server: BUILDING
cd ifood-zuul-server
mvn clean install -U
cd ..
clear

echo iFood Config Server: BUILD DONE
echo iFood Eureka Server: BUILD DONE
echo iFood Zuul Server: BUILD DONE
echo iFood Suggestion Server: BUILDING
cd ifood-suggestion-server
mvn clean install -U
cd ..
clear

echo iFood Config Server: BUILD DONE
echo iFood Eureka Server: BUILD DONE
echo iFood Zuul Server: BUILD DONE
echo iFood Suggestion Server: BUILD DONE
echo ---
echo Starting Application...
docker-compose up --build

sleep 60
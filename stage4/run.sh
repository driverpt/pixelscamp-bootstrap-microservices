#!/bin/bash

mvn clean package

docker-compose up -d kong-database consul

sleep 30s

docker-compose up -d book auth

# We need to wait for the Services to Register with the Consul Service
sleep 60s

docker-compose up -d gateway kong-ui

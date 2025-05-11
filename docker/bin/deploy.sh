#!/bin/bash

# Build backend
mvn clean install -DskipTests

# Restart container to apply updated JAR
docker compose build

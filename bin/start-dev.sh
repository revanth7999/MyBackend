#!/bin/bash

# File: start-dev.sh

set -e  # Exit on any error

cd ..
echo "Building JAR..."
mvn clean install -DskipTests

cd docker
echo "Building Docker image..."
docker compose build

echo "Starting Docker container in detached mode..."
docker compose up -d

cd ..
cd bin
WATCH_SCRIPT="./watch.sh"
echo "Starting watch script in background..."
if [ ! -f "$WATCH_SCRIPT" ]; then
  echo "$WATCH_SCRIPT not found!"
  exit 1
fi

bash "$WATCH_SCRIPT" &

cd ..
cd docker
echo "Starting Docker containers..."
docker compose up

echo "Dev environment is running!"
echo "Watch script PID: $!"

#!/bin/bash

JAR_PATH="/home/revanthdev/src/MyBackend/target/MyBackend-0.0.1-SNAPSHOT.jar"
CONTAINER_NAME="mybackend-app-1"

echo "Watching for changes in $JAR_PATH..."

last_checksum=""

while true; do
  # Wait for a write-close event
  inotifywait -e close_write "$JAR_PATH" >/dev/null 2>&1

  # Wait 2 seconds to allow full write
  sleep 2

  # Check if file is valid (non-zero and changed)
  if [ -f "$JAR_PATH" ] && [ -s "$JAR_PATH" ]; then
    new_checksum=$(md5sum "$JAR_PATH" | awk '{ print $1 }')

    if [ "$new_checksum" != "$last_checksum" ]; then
      echo "Valid JAR change detected. Restarting container..."
      docker compose restart app
      last_checksum="$new_checksum"
    fi
  else
    echo "JAR file is missing or empty. Skipping restart."
  fi
done


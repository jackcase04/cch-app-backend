#!/usr/bin/env bash
set -e

cleanup() {
  echo "Shutting down Docker containers"
  docker compose down
}
trap cleanup EXIT

if [ "$1" = "clean" ]; then
    mvn clean package
else
    mvn package
fi

docker compose build app
docker compose up -d

echo "Waiting for API to start..."
while ! nc -z 127.0.0.1 8080; do
  sleep 1
done

lt --port 8080 --subdomain cch-app-springboot &
LT_PID=$!

docker compose logs -f app
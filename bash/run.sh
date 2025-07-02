#!/usr/bin/env bash
set -e

cleanup() {
  echo "Shutting down Spring Boot"
  kill $SPRING_PID
}
trap cleanup EXIT

echo "Starting API"
mvn spring-boot:run &
SPRING_PID=$!

while ! nc -z 127.0.0.1 8080; do
  sleep 1
done

echo "Opening LocalTunnel"
lt --port 8080 --subdomain cch-app-springboot

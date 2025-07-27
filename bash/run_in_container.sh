#!/usr/bin/env bash
set -e

# Default to local profile
PROFILE="local"

# Parse arguments
while [[ $# -gt 0 ]]; do
  case $1 in
    clean)
      CLEAN=true
      shift
      ;;
    --profile)
      PROFILE="$2"
      shift 2
      ;;
    --rds)
      PROFILE="rds"
      shift
      ;;
    --local)
      PROFILE="local"
      shift
      ;;
    *)
      echo "Unknown option: $1"
      echo "Usage: $0 [clean] [--profile local|rds] [--local] [--rds]"
      exit 1
      ;;
  esac
done

cleanup() {
  echo "Shutting down Docker containers"
  docker compose --profile $PROFILE down
  if [ ! -z "$LT_PID" ]; then
    kill $LT_PID 2>/dev/null || true
  fi
}
trap cleanup EXIT

echo "Using profile: $PROFILE"

if [ "$CLEAN" = true ]; then
    echo "Clean build..."
    mvn clean package
else
    mvn package
fi

docker compose --profile $PROFILE build app-$PROFILE
docker compose --profile $PROFILE up -d

echo "Waiting for API to start..."
while ! nc -z 127.0.0.1 8080; do
  sleep 1
done

lt --port 8080 --subdomain cch-app-springboot &
LT_PID=$!

docker compose --profile $PROFILE logs -f app-$PROFILE
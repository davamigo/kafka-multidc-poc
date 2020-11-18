#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mBuilding, creating and starting the docker containers and networks for the project...\033[0m"
echo -e "\033[32mUsage:\033[0m"
echo -e "\t$0 [--extra][--detach|--no-detach][--build|--no-build][ARGS...]\n"

ARGS=""
EXTRA=""
DETACH="--detach"
BUILD="--build"
while [[ $# -ge 1 ]]; do
  case "$1" in

    "--extra")
      EXTRA="-f docker-compose-extra.yml"
      ;;

    "-d" | "--detach")
      DETACH="--detach"
      ;;

    "-n" | "--no-detach")
      DETACH=""
      ;;

    "--build")
      BUILD="--build"
      ;;

    "--no-build")
      BUILD="--no-build"
      ;;

    *)
      ARGS="${ARGS} $1"
      ;;
  esac
  shift
done;

echo -e "$ docker-compose -f docker-compose.yml -f docker-compose.dev.yml $EXTRA up $BUILD $DETACH $ARGS\n"

docker-compose \
  -f docker-compose.yml \
  -f docker-compose.dev.yml \
  $EXTRA \
  up \
  $BUILD \
  $DETACH \
  $ARGS

echo -e ""

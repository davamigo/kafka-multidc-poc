#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mStopping and destroying the docker containers, networks and volumes for the project...\033[0m"
echo -e "\033[32mUsage:\033[0m"
echo -e "\t$0 [ARGS...]\n"

ARGS=""
while [[ $# -ge 1 ]]; do
  ARGS="${ARGS} $1"
  shift
done;

echo -e "$ docker-compose -f docker-compose.yml -f docker-compose.dev.yml -f docker-compose-extra.yml down -v $ARGS\n"

docker-compose \
  -f docker-compose.yml \
  -f docker-compose.dev.yml \
  -f docker-compose-extra.yml \
  down -v \
  $ARGS

echo -e ""

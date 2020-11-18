#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mListing all the topics in the cluster...\033[0m"

echo -e "\n$ docker exec -t kpoc_broker1 kafka-topics.sh --bootstrap-server=localhost:9092 --list\n"

docker exec -t kpoc_broker1 \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --list

echo -e ""

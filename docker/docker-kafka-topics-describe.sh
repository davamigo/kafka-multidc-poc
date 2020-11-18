#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mDescribing the project topics...\033[0m"

echo -e "\n$ docker exec -t kpoc_broker1 kafka-topics.sh --bootstrap-server=localhost:9092 --describe --topic topic.acks.all\n"

docker exec -t kpoc_broker1 \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --describe \
    --topic topic.acks.all

echo -e "\n$ docker exec -t kpoc_broker1 kafka-topics.sh --bootstrap-server=localhost:9092 --describe --topic topic.acks.1\n"

docker exec -t kpoc_broker1 \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --describe \
    --topic topic.acks.1

echo -e "\n$ docker exec -t kpoc_broker1 kafka-topics.sh --bootstrap-server=localhost:9092 --describe --topic topic.acks.0\n"

docker exec -t kpoc_broker1 \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --describe \
    --topic topic.acks.0

echo -e ""

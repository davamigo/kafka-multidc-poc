#!/bin/bash

cd $(dirname $0)

echo -e "Creating Docker container from image blacktop/kafka:2.6vp..."

docker run --rm -d \
  --name blacktop_kafka_2.6vp \
  -p 2181:2181 \
  -p 9092:9092 \
  -e KAFKA_ADVERTISED_HOST_NAME=localhost \
  blacktop/kafka:2.6vp

echo -e ""

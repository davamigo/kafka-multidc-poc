#!/bin/bash

cd $(dirname $0)

echo -e "Usage:"
echo -e "\t$0 TOPIC [ARGS...]\n"

TOPIC="$1"
shift

ARGS=""
while [[ $# -ge 1 ]]; do
    ARGS="${ARGS} $1"
    shift
done;

if [ "$TOPIC" == "" ]; then
  echo -e "Error:"
  echo -e "\t\033[31mThe \033[0mTOPIC\033[31m is mandatory...\033[0m\n"
  exit
fi

echo -e "\033[33mStarting consumer for topic $TOPIC...\033[0m\n"

echo -e "\n$ docker exec -t kpoc_broker1 kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic $TOPIC --from-beginning $ARGS\n"

docker exec -t kpoc_broker1 \
  kafka-console-consumer.sh \
    --bootstrap-server=localhost:9092 \
    --topic $TOPIC \
    --from-beginning \
    $ARGS

echo -e ""

#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mThis script starts a consumer from beginning.\033[0m"
echo -e "\033[32mUsage:\033[0m"
echo -e "\t$0 TOPIC [ARGS...]\n"

TOPIC="$1"
shift

ARGS=""
while [[ $# -ge 1 ]]; do
    ARGS="${ARGS} $1"
    shift
done;

if [ "$TOPIC" == "" ]; then
  echo -e "\033[31mError:\033[0m The TOPIC is mandatory!\n"
  exit
fi

echo -e "\033[33mStarting consumer for topic $TOPIC...\033[0m\n"
echo -e "$ docker exec -t kpoc_broker1 kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic $TOPIC --from-beginning $ARGS\n"

docker exec -it kpoc_broker1 \
  kafka-console-consumer.sh \
    --bootstrap-server=localhost:9092 \
    --topic $TOPIC \
    --from-beginning \
    $ARGS

echo -e ""

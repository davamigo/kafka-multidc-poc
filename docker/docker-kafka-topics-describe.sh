#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mThis script describes a topic.\033[0m"
echo -e "\033[32mUsage:\033[0m"
echo -e "\t$0 TOPIC [BROKER]\n"

TOPIC="$1"
shift

if [ "$TOPIC" == "" ]; then
  echo -e "\033[31mError:\033[0m The TOPIC is mandatory!\n"
  exit
fi

BROKER="$1"
shift

if [ "$BROKER" == "" ]; then
  BROKER="kpoc_broker1"
fi

echo -e "\033[33mDescribing the topic $TOPIC...\033[0m\n"
echo -e "$ docker exec -t $BROKER kafka-topics.sh --bootstrap-server=localhost:9092 --describe --topic $TOPIC\n"

docker exec -t $BROKER \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --describe \
    --topic $TOPIC

echo -e ""

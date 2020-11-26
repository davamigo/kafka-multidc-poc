#!/bin/bash

cd $(dirname $0)

echo -e "\033[33mListing all the topics in the cluster...\033[0m"
echo -e "\033[32mUsage:\033[0m"
echo -e "\t$0 [BROKER]\n"

BROKER="$1"
shift

if [ "$BROKER" == "" ]; then
  BROKER="kpoc_broker1"
fi

echo -e "\n$ docker exec -t $BROKER kafka-topics.sh --bootstrap-server=localhost:9092 --list\n"

docker exec -t $BROKER \
  kafka-topics.sh \
    --bootstrap-server=localhost:9092 \
    --list

echo -e ""

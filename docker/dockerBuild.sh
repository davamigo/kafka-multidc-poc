#!/bin/bash

cd $(dirname $0)

echo -e "Building Docker image blacktop/kafka:2.6vp..."

docker build -t blacktop/kafka:2.6vp .

echo -e ""

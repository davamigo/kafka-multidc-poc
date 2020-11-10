#!/bin/bash

set -eo pipefail

echo "Checking ENV vars..."
for VAR in `env`
do
  if [[ $VAR =~ ^ZOOKEEPER_ && ! $VAR =~ ^ZOOKEEPER_MYID ]]; then
    zookeeper_name=`echo "$VAR" | sed -r "s/ZOOKEEPER_(.*)=.*/\1/g" | tr '[:upper:]' '[:lower:]' | tr _ .`
    env_var=`echo "$VAR" | sed -r "s/(.*)=.*/\1/g"`
    echo "DYNAMIC CONFIG========================================================================="
    echo "$zookeeper_name=${!env_var}"
    if egrep -q "(^|^#)$zookeeper_name=" config/zookeeper.properties; then
        sed -r -i "s@(^|^#)($zookeeper_name)=(.*)@\2=${!env_var}@g" config/zookeeper.properties
    else
        echo "$zookeeper_name=${!env_var}" >> config/zookeeper.properties
    fi
  fi
done

if [[ -z "$ZOOKEEPER_PORT" ]]; then
    export ZOOKEEPER_PORT=2181
fi

if [[ -z "$ZOOKEEPER_DATA" ]]; then
    export ZOOKEEPER_DATA=/tmp/zookeeper
fi

if [[ -z "$ZOOKEEPER_MYID" ]]; then
    export ZOOKEEPER_MYID=1
fi

# Make data dirs
mkdir -p $ZOOKEEPER_DATA
chown -R kafka:kafka $ZOOKEEPER_DATA

# Create myid file
echo "$ZOOKEEPER_MYID" > ${ZOOKEEPER_DATA}/myid

#!/bin/bash
if [ "${DETACH}" = true ]; then
    docker-compose \
        --file docker-compose-dev.yml \
        --env-file .env.dev \
        --detach \
        up || exit 1
else
    docker-compose \
        --file docker-compose-dev.yml \
        --env-file .env.dev \
        up || exit 1
fi

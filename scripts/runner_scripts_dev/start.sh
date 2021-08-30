#!/bin/bash
if [ "${DETACH}" = true ]; then
    docker-compose up \
        --file docker-compose-dev.yml \
        --env-file .env.dev \
        --detach || exit 1
else
    docker-compose up \
        --file docker-compose-dev.yml \
        --env-file .env.dev || exit 1
fi

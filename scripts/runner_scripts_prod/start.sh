#!/bin/bash
if [ "${DETACH}" = true ]; then
    docker-compose up \
        --file docker-compose-prod.yml \
        --env-file .env.prod \
        --detach || exit 1
else
    docker-compose up \
        --file docker-compose-prod.yml \
        --env-file .env.prod || exit 1
fi

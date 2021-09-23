#!/bin/bash
if [ "${DETACH}" = true ]; then
    docker-compose \
        --file docker-compose-prod.yml \
        --env-file .env.prod \
        up --detach || exit 1
else
    docker-compose \
        --file docker-compose-prod.yml \
        --env-file .env.prod \
        up || exit 1
fi

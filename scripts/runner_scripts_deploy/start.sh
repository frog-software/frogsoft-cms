#!/bin/bash
if [ "${DETACH}" = true ]; then
    docker-compose \
        --file docker-compose-prod.yml \
        --env-file .env.prod \
        up --no-build --detach || exit 1
else
    docker-compose \
        --file docker-compose-prod.yml \
        --env-file .env.prod \
        up --no-build || exit 1
fi

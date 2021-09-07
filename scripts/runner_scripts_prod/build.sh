#!/bin/bash
docker-compose \
    --file docker-compose-prod.yml \
    --env-file .env.prod \
    build || exit 1

docker-compose \
    --file docker-compose-prod.yml \
    --env-file .env.prod \
    push || exit 1

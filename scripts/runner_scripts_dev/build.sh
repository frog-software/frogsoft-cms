#!/bin/bash
docker-compose \
    --file docker-compose-dev.yml \
    --env-file .env.dev \
    build || exit 1

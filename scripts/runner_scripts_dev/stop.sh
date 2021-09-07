#!/bin/bash
docker-compose \
    --file docker-compose-dev.yml \
    --env-file .env.dev \
    rm -s -f || exit 1
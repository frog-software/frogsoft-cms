#!/bin/bash
docker-compose \
    --file docker-compose-prod.yml \
    --env-file .env.prod \
    rm -s -f || exit 1
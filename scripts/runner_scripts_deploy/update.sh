#!/bin/bash
docker-compose \
    --file docker-compose-prod.yml \
    --env-file .env.prod \
    pull || exit 1
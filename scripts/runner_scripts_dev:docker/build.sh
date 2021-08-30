#!/bin/bash
docker-compose build \
    --file docker-compose-dev.yml \
    --env-file .env.dev || exit 1

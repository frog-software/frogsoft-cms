#!/bin/bash
docker-compose build \
    --file docker-compose-prod.yml \
    --env-file .env.prod || exit 1

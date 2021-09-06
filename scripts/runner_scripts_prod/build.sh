#!/bin/bash
docker-compose build \
    --file docker-compose-prod.yml \
    --env-file .env.prod \
    --compress \
    --parallel || exit 1

docker-compose push \
    --file docker-compose-prod.yml || exit 1
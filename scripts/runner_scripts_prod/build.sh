#!/bin/bash

# backend
cd backend && docker build --tag "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" . || exit 1
docker push "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" || exit 1
cd ..

# frontend-user
cd frontend-user && docker build --tag "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" . || exit 1
docker push "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" || exit 1
cd ..

# backend
cd backend && docker build --tag "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" . || exit 1
docker push "${DOCKERHUB_USERNAME}/frogsoft-cms-backend" || exit 1
cd ..
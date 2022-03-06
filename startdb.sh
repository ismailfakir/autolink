#!/bin/bash
echo "starting mongodb database"
cd database
docker-compose up -d
cd ..
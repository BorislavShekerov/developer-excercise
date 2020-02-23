#!/bin/bash
set -e
 
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

CREATE DATABASE till_database ENCODING UTF8;
GRANT ALL PRIVILEGES ON DATABASE till_database TO postgres;

EOSQL
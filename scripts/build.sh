#!/usr/bin/env bash

set -e

./gradlew :neoforge:s3:build "$@"

#!/usr/bin/env bash

jar=$(ls neoforge/s3/build/libs/s3-*.jar 2>/dev/null | sort -V | tail -1)
username=${1:-"Steven Tompkins"}
modpack=${2:-"ezstorage-test-env"}

if [ -z "$jar" ]; then
  echo "No jar found in neoforge/s3/build/libs/. Run ./gradlew :neoforge:s3:build first."
  exit 1
fi

modsdir="/mnt/c/Users/${username}/AppData/Roaming/gdlauncher_carbon/data/instances/${modpack}/instance/mods"

# Remove old S3 jars to avoid duplicate mod conflicts
rm -f "$modsdir"/s3-*.jar

echo "Copying $jar"
cp "$jar" "$modsdir"

advanced_jar=$(ls neoforge/s3-advanced/build/libs/s3-advanced-*.jar 2>/dev/null | sort -V | tail -1)
if [ -n "$advanced_jar" ]; then
  echo "Copying $advanced_jar"
  cp "$advanced_jar" "$modsdir"
fi

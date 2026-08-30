#!/usr/bin/env sh
# This is a wrapper that will download gradle if needed, but for GitHub we use system gradle
set -e
DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  exec java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  exec gradle "$@"
fi

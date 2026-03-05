#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
MODS_DIR="$PROJECT_ROOT/server/mods"

usage() {
    echo "Usage: $0 {start|stop|restart|logs|clean}"
    exit 1
}

check_docker() {
    if ! command -v docker &>/dev/null; then
        echo "Error: docker is not installed or not in PATH."
        exit 1
    fi
    if ! docker compose version &>/dev/null; then
        echo "Error: docker compose is not available."
        exit 1
    fi
}

copy_mod() {
    local jar
    jar=$(ls "$PROJECT_ROOT"/neoforge/build/libs/s3-*.jar 2>/dev/null | sort -V | tail -1)

    if [ -z "$jar" ]; then
        echo "Error: No jar found in neoforge/build/libs/. Run ./gradlew :neoforge:build first."
        exit 1
    fi

    mkdir -p "$MODS_DIR"
    rm -f "$MODS_DIR"/s3-*.jar
    cp "$jar" "$MODS_DIR/"
    echo "Copied $(basename "$jar") to server/mods/"
}

cmd_start() {
    check_docker
    copy_mod
    cd "$PROJECT_ROOT"
    docker compose up -d
    echo "Server starting. Use '$0 logs' to follow output."
}

cmd_stop() {
    check_docker
    cd "$PROJECT_ROOT"
    docker compose stop
}

cmd_restart() {
    cmd_stop
    cmd_start
}

cmd_logs() {
    check_docker
    cd "$PROJECT_ROOT"
    docker compose logs -f
}

cmd_clean() {
    check_docker
    cd "$PROJECT_ROOT"
    docker compose down -v
    rm -rf "$MODS_DIR"
    echo "Server data and mods cleared."
}

if [ $# -lt 1 ]; then
    usage
fi

case "$1" in
    start)   cmd_start ;;
    stop)    cmd_stop ;;
    restart) cmd_restart ;;
    logs)    cmd_logs ;;
    clean)   cmd_clean ;;
    *)       usage ;;
esac

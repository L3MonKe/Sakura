#!/usr/bin/env bash
if [[ -n "$JAVA_HOME" ]]; then
    JAVA_CMD="$JAVA_HOME/bin/java"
else
    JAVA_CMD="java"
fi

# Ensure Java is available
if ! command -v "$JAVA_CMD" &> /dev/null; then
    echo "Error: Java not found. Please set JAVA_HOME or ensure 'java' is in your PATH."
    exit 1
fi

CPATH=$(realpath "$(dirname "$0")")
CP="$CPATH/gui-1.0.8.jar:$CPATH/core-1.0.8.jar:$CPATH/libs/*"

"$JAVA_CMD" -cp "$CP" me.x150.j2cc.gui.Main "$@"

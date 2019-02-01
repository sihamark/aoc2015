#!/bin/sh

if [ -z "$1" ]
then
    echo "please supply the day"
    exit 0
fi

mkdir -p "day$1/src/main/kotlin/aoc2015/day$1"
touch "day$1/build.gradle"
touch "day$1/src/main/kotlin/aoc2015/day$1/main.kt"

(echo "package aoc2015.day$1

import aoc2015.prepare
import aoc2015.start

fun main(args: Array<String>) {
     prepare(
             {\"\"},
             {\"\"}
     ).start(args)
}")>day$1/src/main/kotlin/aoc2015/day$1/main.kt

echo "created module for day$1"
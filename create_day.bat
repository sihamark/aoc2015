mkdir day%1\src\main\kotlin\aoc2015\day%1
echo. 2>day%1\build.gradle
echo. 2>day%1\src\main\kotlin\aoc2015\day%1\main.kt

(echo package aoc2015.day%1^

^

import aoc2015.prepare^

import aoc2015.start^

^

fun main^(args: Array^<String^>^) {^

     prepare^(^

             {""},^

             {""}^

     ^).start^(args^)^

})>day%1\src\main\kotlin\aoc2015\day%1\main.kt
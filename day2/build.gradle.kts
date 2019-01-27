plugins {
    kotlin("jvm") version "1.3.20"
    application
}

application {
    mainClassName = "aoc2015.day2.MainKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
}
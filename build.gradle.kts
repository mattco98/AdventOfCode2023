plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "me.mattco"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("me.mattco.aoc2023.Day") 
}

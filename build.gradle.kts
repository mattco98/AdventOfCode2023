plugins {
    kotlin("jvm") version "1.9.0"
}

group = "me.mattco"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

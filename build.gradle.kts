plugins {
    kotlin("jvm") version "1.9.10"
    id("maven-publish")
}

group = "dev.h4kt"
version = "1.0"

val ktorVersion = "2.3.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
}

kotlin {
    jvmToolchain(8)
}

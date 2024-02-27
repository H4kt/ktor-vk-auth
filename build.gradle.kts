plugins {

    kotlin("jvm") version "1.9.22"

    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "4.0.0"

}

group = "dev.h4kt"
version = "1.0.1"

val ktorVersion: String by project

val ossrhUsername = System.getenv("OSSRH_USERNAME")
    ?: env.fetchOrNull("OSSRH_USERNAME")

val ossrhPassword = System.getenv("OSSRH_PASSWORD")
    ?: env.fetchOrNull("OSSRH_PASSWORD")

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

}

kotlin {
    jvmToolchain(8)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {

    repositories {
        maven {

            name = "H4kt"
            url = uri("https://repo.h4kt.dev/releases")

            authentication {
                create<BasicAuthentication>("basic")
            }

            credentials {
                username = env.H4KT_REPO_USERNAME.orNull() ?: System.getenv("H4KT_REPO_USERNAME")
                password = env.H4KT_REPO_PASSWORD.orNull() ?: System.getenv("H4KT_REPO_PASSWORD")
            }

        }
    }

    publications.register("KtorVkAuth", MavenPublication::class) {
        artifactId = "ktor-vk-auth"
        from(components["java"])
    }

}

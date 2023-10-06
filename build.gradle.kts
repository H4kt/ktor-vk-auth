plugins {

    kotlin("jvm") version "1.9.10"

    id("signing")
    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "1.2.0"
    id("io.codearte.nexus-staging") version "0.30.0"

}

group = "dev.h4kt"
version = "1.0.1"

val ktorVersion = "2.3.4"

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
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")

}

kotlin {
    jvmToolchain(8)
}

java {
    withSourcesJar()
    withJavadocJar()
}

nexusStaging {

    packageGroup = "dev.h4kt.xposed"

    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    username = ossrhUsername
    password = ossrhPassword

}

publishing {

    repositories {
        maven {

            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }

        }
    }

    publications.register("KtorVkAuth", MavenPublication::class) {

        artifactId = "ktor-vk-auth"
        from(components["java"])

        pom {

            packaging = "jar"

            name.set("Ktor-vk-auth")
            url.set("https://github.com/H4kt/ktor-vk-auth")
            description.set("A simple implementation of VK mini app authentication for Ktor")

            scm {
                connection.set("scm:https://github.com/H4kt/ktor-vk-auth.git")
                developerConnection.set("scm:git@github.com:H4kt/ktor-vk-auth.git")
                url.set("https://github.com/H4kt/ktor-vk-auth")
            }

            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }

            developers {
                developer {
                    id.set("H4kt")
                    name.set("H4kt")
                    email.set("h4ktoff@gmail.com")
                }
            }

        }

    }

    signing {
        publishing.publications.forEach(::sign)
    }

}

![Kotlin version badge](https://img.shields.io/badge/kotlin-1.9.10-blue)
![Maven central badge](https://maven-badges.herokuapp.com/maven-central/dev.h4kt.ktor-vk-auth/ktor-vk-auth/badge.svg?style=flat)

# ktor-vk-auth

A simple vk mini app authentication library for Ktor

## Installation
### Gradle
#### Kotlin
```kotlin
repositories {
  mavenCentral()
}

dependencies {
  implementation("dev.h4kt:ktor-vk-auth:1.0")
}
```

## Usage
Using [Ktor authentication plugin](https://ktor.io/docs/authentication.html) configure a VK authentication by providing it with a secret key and a function to retrieve a user from your database

App.kt
```kotlin
fun Application.module() {
  configureAuthentication()
}
```

plugins/Authentication.kt
```kotlin
fun Application.configureAuthentication() = authentication {
  vk("<name>") {

    secretKey = "<your-secret-key-goes-here>"

    authenticate { vkUserId ->
      // Here your code executes within Application context
      // Retrieve a user from your database or create a new one
      // If needed, return null to proceed with failed authentication
    }

  }
}
```

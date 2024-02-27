[![Build](https://github.com/H4kt/ktor-vk-auth/actions/workflows/deploy.yml/publish.svg)](https://github.com/H4kt/ktor-vk-auth/actions/workflows/publish.yml)
![Maven latest version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.h4kt.dev%2Freleases%2Fdev%2Fh4kt%2Fktor-vk-auth%2Fmaven-metadata.xml&logo=apachemaven)
![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-purple?logo=Kotlin&label=Kotlin)

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

# Yandex MapKit KMP SDK

[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.8.0-blue)](https://github.com/JetBrains/compose-multiplatform)
[![License](https://img.shields.io/badge/License-Apache/2.0-blue.svg)](https://github.com/SuLG-ik/yandex-mapkit-kmp/blob/main/LICENSE)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat&color=blue)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat&color=blue)

Kotlin SDK for Yandex MapKit with custom settings. This library supports compose multiplatform (iOS or Android).

**_NOTE:_ By now there's no remote publication and library is under development. Only local publication was used so far.**

**_NOTE:_ It is not Yandex's project. Author has no connection with original SDK, it is wrapper
above official Yandex MapKit SDK**

# Available libraries

The following libraries are available. It
uses [Yandex MapKit SDK](https://yandex.ru/dev/mapkit/doc/ru/) version *4.15.0-lite*

| Module	                                        | Gradle Dependency                                                                                                                            | Description                                                                                                                                              |
|------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Compose](yandex-mapkit-kmp-compose)           | `ru.nikfirs.mapkit:yandex-mapkit-kmp-compose:0.0.1-lite`    | Component to draw map and [compose-resources](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-images-resources.html) usage as map images |

## Installation

The minimum supported Android SDK is 26 (Android 8.0).

For iOS platform install dependency of official [Yandex MapKit SDK](https://yandex.ru/dev/mapkit/doc/ru/ios/generated/getting_started) of corresponding version (4.15.0-lite)
  This can be done through your preferred installation
method ([Cocoapods](https://kotlinlang.org/docs/native-cocoapods.html)/[SPM](https://kotlinlang.org/docs/native-spm.html#project-configuration-options)).

```kotlin
kotlin {
  cocoapods {
    pod("YandexMapsMobile") {
      version = "<mapkit-version>"
    }
  }
  sourceSets {
    commonMain.dependencies {
      implementation("ru.nikfirs.mapkit:yandex-mapkit-kmp:<version>") // main module
      implementation("ru.nikfirs.mapkit:yandex-mapkit-kmp-compose:<version>") // optional compose support
    }
  }
}
```

```kotlin
cocoapods {
    pod("YandexMapsMobile") {
        version = "<version>"
    }
}
```
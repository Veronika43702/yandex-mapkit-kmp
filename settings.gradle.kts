rootProject.name = "yandex-mapkit-kmp"
include(":yandex-mapkit-kmp")
include(":yandex-mapkit-kmp-compose")
include(":sample:composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "yandex-mapkit-kmp"
include(":yandex-mapkit-kmp")
include(":yandex-mapkit-kmp-compose")

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

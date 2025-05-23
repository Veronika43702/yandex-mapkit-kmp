import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.cocoapods)
    id("maven-publish")
}

val supportIosTarget = project.property("skipIosTarget") != "true"
group = "ru.nikfirs.mapkit"
version = extra["library_version"].toString()

kotlin {
    androidTarget {
        publishAllLibraryVariants()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }

    if (supportIosTarget) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        cocoapods {
            ios.deploymentTarget = "15.0"
            framework {
                baseName = "YandexMapKitKMP"
                linkerOpts("-framework", "SystemConfiguration")
            }
            noPodspec()
            pod("YandexMapsMobile") {
                version = libs.versions.yandex.mapkit.get()
                packageName = "YandexMapKit"
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                progressiveMode = true
                if (name.lowercase().contains("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                    optIn("kotlinx.cinterop.BetaInteropApi")
                }
            }
        }

        androidMain.dependencies {
            api(libs.yandex.mapkit)
        }
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-api=strict")
        freeCompilerArgs.add("-Xexpect-actual-classes")
        freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    }
}

android {
    namespace = "ru.nikfirs.mapkit"
    compileSdk =  libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk =  libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.plugin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.atomicfu)
}

val supportIosTarget = project.property("skipIosTarget") != "true"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    if (supportIosTarget) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        cocoapods {
            ios.deploymentTarget = "15.0"
            framework {
                baseName = "YandexMapKitKMPCompose"
                linkerOpts = mutableListOf("-framework", "CoreLocation", "-framework", "SystemConfiguration")
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
        commonMain.dependencies {
            api(project(":yandex-mapkit-kmp"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.animation) {
                exclude(group = "androidx.annotation")
                exclude(group = "androidx.collection")
            }
            implementation(libs.lifecycle.compose)
            api(libs.lifecycle.runtime)
            implementation(libs.atomicfu)
            api(libs.kotlinx.collections)
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
        val stabilityConfigurationFile =
            layout.projectDirectory.file("compose_compiler_stability_config.conf").asFile
        freeCompilerArgs.add("-Xexplicit-api=strict")
        freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${stabilityConfigurationFile.absolutePath}"
        )
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")

        if (findProperty("composeCompilerReports") == "true") {
            freeCompilerArgs.addAll(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${
                    layout.buildDirectory.dir(
                        "compose_compiler"
                    ).get()
                }",
            )
        }
        if (findProperty("composeCompilerMetrics") == "true") {
            freeCompilerArgs.addAll(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${
                    layout.buildDirectory.dir(
                        "compose_compiler"
                    ).get()
                }",
            )
        }
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}


tasks.withType<KotlinCompilationTask<*>> {
    compilerOptions.freeCompilerArgs.add("-opt-in=kotlinx.cinterop.ExperimentalForeignApi")
}


android {
    namespace = "ru.nikfirs.mapkit.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk =  libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}
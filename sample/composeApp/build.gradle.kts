import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.plugin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts = mutableListOf("-framework", "CoreLocation", "-framework", "SystemConfiguration")
        }
        pod("YandexMapsMobile") {
            version = libs.versions.yandex.mapkit.get()
            packageName = "YandexMapKit"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.ui.tooling)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(project(":yandex-mapkit-kmp-compose"))
            implementation(compose.components.uiToolingPreview)
        }
    }
}

android {
    namespace = "ru.nikfirs.mapkit.sample"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMainOld/resources")

    defaultConfig {
        applicationId = "ru.nikfirs.mapkit.sample"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    buildFeatures {
        compose = true
    }
}


buildkonfig {
    packageName = "ru.nikfirs.mapkit.sample"
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "MAPKIT_API_KEY",
            getMapkitApiKey(),
            const = true
        )
    }
}

fun getMapkitApiKey(): String {
    return try {
        val properties = Properties()
        rootProject.file("local.properties").inputStream().use { properties.load(it) }
        val value = properties.getProperty("MAPKIT_API_KEY", "")
        if (value.isEmpty()) {
            throw InvalidUserDataException("MapKit API key is not provided. Set your API key in the project's local.properties file: `MAPKIT_API_KEY=<your-api-key-value>`.")
        }
        value
    } catch (e: Exception) {
        project.findProperty("signingKey") as String? ?: System.getenv()["signingKey"] ?: ""
    }
}
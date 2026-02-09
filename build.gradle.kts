import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

group = "uz.yalla"
version = "2.0.0"

kotlin {
    targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
        namespace = "uz.yalla.components"
        compileSdk = 36
        minSdk = 26

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "YallaComponents"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Yalla libraries
            implementation(libs.yalla.design)
            implementation(libs.yalla.resources)
            implementation(libs.yalla.platform)

            // Compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)

            // DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            // Architecture
            implementation(libs.orbit.core)
            implementation(libs.orbit.viewmodel)
            implementation(libs.orbit.compose)

            // Image loading
            implementation(libs.coil)
            implementation(libs.coil.compose)

            // Coroutines & Serialization
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Location
            implementation(libs.geo)
            implementation(libs.geo.compose)

            // Connectivity
            implementation(libs.connectivity.device)

            // iOS-style components
            implementation(libs.cupertino)

            // Lottie animations
            implementation(libs.compottie)
            implementation(libs.compottie.resources)

            // Layout
            implementation(libs.constraintlayout)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.android)
            implementation(libs.datetime.wheel.picker)
        }

        iosMain.dependencies {
            // iOS-specific dependencies if needed
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            artifactId = artifactId.replace(project.name, "components")
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RoyalTaxi/yalla-components")
            credentials {
                username = findProperty("gpr.user") as String?
                    ?: System.getenv("GITHUB_ACTOR")
                password = findProperty("gpr.key") as String?
                    ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

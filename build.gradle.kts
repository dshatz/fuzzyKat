import org.jetbrains.compose.ComposeExtension

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.app) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

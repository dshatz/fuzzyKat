plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm("desktop")
    jvmToolchain(17)


    sourceSets {
        val desktopMain by getting {
            dependencies {
                api(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
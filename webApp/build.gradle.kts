import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    applyDefaultHierarchyTemplate()
    js(IR) {
        moduleName = "sample"
        browser {
            commonWebpackConfig {
                outputFileName = "sample.js"
                experiments.add("topLevelAwait")
            }
        }
        binaries.executable()
    }

    wasmJs {
        moduleName = "sample"
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {

                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.rootDir.path)
                        add(project.rootDir.path + "/shared/")
                        add(project.rootDir.path + "/nonAndroidMain/")
                        add(project.rootDir.path + "/webApp/")
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsWasmMain by creating {
            dependencies {
                implementation(project(":shared"))
            }
        }
        val wasmJsMain by getting {
            dependsOn(jsWasmMain)
            dependencies {
            }
        }
        val jsMain by getting {
            dependsOn(jsWasmMain)
            dependencies {
            }
        }
    }
}

compose.experimental {
    web.application {}
}

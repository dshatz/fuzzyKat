import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    androidTarget {
        publishLibraryVariants("release")
    }
    jvmToolchain(17)
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    linuxArm64()
    mingwX64()
    macosArm64()
    macosX64()
    androidNativeX64()
    androidNativeX86()
    androidNativeArm32()
    androidNativeArm64()
    js(IR) {
        moduleName = "fuzzyKat.js"
        browser()
        binaries.library()
    }
    wasmJs {
        moduleName = "fuzzyKat.js"
        browser()
        binaries.library()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {}

        val androidMain by getting {
            dependsOn(jvmMain)
        }

        val jsWasmMain by creating {
            dependsOn(commonMain)
        }

        val jsMain by getting {

        }

        val wasmJsMain by getting {
            dependsOn(jsWasmMain)
        }
    }
}

android {
    namespace = "com.dshatz.fuzzyKat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}


mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()

    val version = System.getenv("PUBLISH_VERSION")
    coordinates("com.dshatz.fuzzyKat", "fuzzyKat", version)

    pom {
        name.set(project.name)
        description.set("Fuzzy search for Kotlin Multiplatform")
        inceptionYear.set("2024")
        url.set("https://github.com/dshatz/fuzzyKat/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("dshatz")
                name.set("Daniels Šatcs")
                url.set("https://github.com/dshatz/")
            }
        }
        scm {
            url.set("https://github.com/dshatz/fuzzyKat/")
            connection.set("scm:git:git://github.com/dshatz/fuzzyKat.git")
            developerConnection.set("scm:git:ssh://git@github.com/dshatz/fuzzyKat.git")
        }
    }
}

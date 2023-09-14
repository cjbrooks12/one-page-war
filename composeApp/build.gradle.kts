plugins {
    id("copper-leaf-base")
    id("copper-leaf-android-application")
    id("copper-leaf-targets")
//    id("copper-leaf-cocoapods")
    id("copper-leaf-kotest")
    id("copper-leaf-compose")
    id("copper-leaf-serialization")
    id("copper-leaf-buildConfig")
//    id("copper-leaf-resources")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)

                implementation(libs.ballast.core)
                implementation(libs.ballast.navigation)
                implementation(libs.ballast.repository)
                implementation(libs.ballast.savedState)
                implementation(libs.ballast.undo)
                implementation("io.github.copper-leaf:ballast-debugger-client:4.0.0")

                implementation(libs.composeImageLoader)
                implementation(libs.kermit)

                implementation(libs.ktor.client.core)
                implementation(libs.kotlinx.datetime)

                implementation(libs.multiplatformSettings.core)
                implementation(libs.multiplatformSettings.noArg)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.material)
                implementation(libs.androidx.activityCompose)

                implementation(libs.bundles.ktorClient)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(libs.ktor.client.js)
            }
        }

//        val iosMain by getting {
//            dependencies {
//                implementation(libs.ktor.client.darwin)
//            }
//        }
    }
}

compose {
    experimental {
        web {
            application {

            }
        }
    }
}

buildConfig {

}

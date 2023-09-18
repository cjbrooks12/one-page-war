pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

val conventionDir = "./gradle-convention-plugins"
//val conventionDir = "./../../copperleaf/gradle-convention-plugins"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("$conventionDir/gradle/conventionLibs.versions.toml"))
        }
    }
}

includeBuild(conventionDir)

rootProject.name = "one-page-war"

include(":composeApp")

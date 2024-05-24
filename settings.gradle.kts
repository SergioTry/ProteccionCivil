pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Dependencia necesaria para los iconos de carga
        maven(url = "https://jitpack.io")
        google()
        mavenCentral()
    }
}

rootProject.name = "ProteccionCivil"
include(":app")

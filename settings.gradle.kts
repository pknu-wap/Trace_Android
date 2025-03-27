pluginManagement {
    includeBuild("build-logic")
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
        google()
        mavenCentral()
    }
}

rootProject.name = "trace"

include(":app")

include(":core")
include(":core:data")
include(":core:designsystem")
include(":coree:domain")
include(":core:common")
include(":core:network")
include(":core:navigation")
include(":core:domain")

include(":feature")
include(":feature:main")
include(":feature:auth")

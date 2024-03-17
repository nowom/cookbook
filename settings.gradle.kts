pluginManagement {
    repositories {
        google()
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

rootProject.name = "Cookbook"
include(":app")
include(":core:domain")
include(":core:data")
include(":feature:recipes")
include(":feature:shopping")
include(":feature:home")
include(":core:database")
include(":core:model")
include(":core:common")
include(":feature:recipedetails")
include(":core:design-system")

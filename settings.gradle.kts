enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    val detekt_version: String by settings

    plugins {
        id("io.gitlab.arturbosch.detekt") version detekt_version
    }
}

include(":core")
include(":examples")

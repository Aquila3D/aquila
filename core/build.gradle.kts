plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.gitlab.arturbosch.detekt") version "1.2.0"
}

kotlin {
    js {
        browser {}
        nodejs {}
    }
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    //mingwX64("mingw")
    //linuxX64()
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
        }
        sourceSets["commonMain"].dependencies {
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${findProperty("kotlin_coroutines_version")}")
            implementation("com.ToxicBakery.logging:common:${findProperty("arbor_version")}")
        }
        sourceSets["commonTest"].dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }
        sourceSets["jvmMain"].apply {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${findProperty("kotlin_coroutines_version")}")
            }
        }
        sourceSets["jvmTest"].dependencies {
            implementation(kotlin("test-junit"))
        }
        sourceSets["jsMain"].dependencies {
            implementation(kotlin("stdlib-js"))
            implementation("org.jetbrains.kotlinx:kotlinx-html-js:${findProperty("kotlin_html_version")}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${findProperty("kotlin_coroutines_version")}")
        }
        /*sourceSets["mingwMain"].dependencies {
        }
        sourceSets["mingwTest"].dependencies {
        }*/
    }
}

detekt {
    failFast = true
    buildUponDefaultConfig = true
    config = files("${rootProject.projectDir}/detekt/config.yml")
    input = files(
        kotlin.sourceSets
            .flatMap { sourceSet -> sourceSet.kotlin.srcDirs }
            .map { file -> file.relativeTo(projectDir) }
    )
    reports {
        html.enabled = true
    }
}

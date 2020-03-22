import org.gradle.internal.os.OperatingSystem

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.gitlab.arturbosch.detekt")
}

val lwjglVersion = "3.2.3"

val lwjglNatives = when (OperatingSystem.current()) {
    OperatingSystem.LINUX   -> System.getProperty("os.arch").let {
        if (it.startsWith("arm") || it.startsWith("aarch64"))
            "natives-linux-${if (it.contains("64") || it.startsWith("armv8")) "arm64" else "arm32"}"
        else
            "natives-linux"
    }
    OperatingSystem.MAC_OS  -> "natives-macos"
    OperatingSystem.WINDOWS -> "natives-windows"
    else -> throw Error("Unrecognized or unsupported Operating system. Please set \"lwjglNatives\" manually")
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
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
        }
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${findProperty("kotlin_coroutines_version")}")
                implementation("com.ToxicBakery.logging:common:${findProperty("arbor_version")}")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        sourceSets["jvmMain"].dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${findProperty("kotlin_coroutines_version")}")

            implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

            implementation("org.lwjgl:lwjgl:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-assimp:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-openal:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-stb:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-vma:$lwjglVersion")
            implementation("org.lwjgl:lwjgl-vulkan:$lwjglVersion")
            runtimeOnly("org.lwjgl:lwjgl$lwjglVersion", classifier = lwjglNatives)
            runtimeOnly("org.lwjgl:lwjgl-assimp:$lwjglVersion", classifier = lwjglNatives)
            runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion", classifier = lwjglNatives)
            runtimeOnly("org.lwjgl:lwjgl-openal:$lwjglVersion", classifier = lwjglNatives)
            runtimeOnly("org.lwjgl:lwjgl-stb:$lwjglVersion", classifier = lwjglNatives)
            runtimeOnly("org.lwjgl:lwjgl-vma:$lwjglVersion", classifier = lwjglNatives)
            if (lwjglNatives == "natives-macos") {
                runtimeOnly("org.lwjgl:lwjgl-vulkan:$lwjglVersion", classifier = lwjglNatives)
            }
        }
        sourceSets["jvmTest"].dependencies {
            implementation(kotlin("test-junit"))
        }
        sourceSets["jsMain"].dependencies {
            implementation(kotlin("stdlib-js"))
            implementation("org.jetbrains.kotlinx:kotlinx-html-js:${findProperty("kotlin_html_version")}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${findProperty("kotlin_coroutines_version")}")

            api(npm("nvk"))
        }
        sourceSets["jsTest"].dependencies {
            implementation(kotlin("test-js"))
        }
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

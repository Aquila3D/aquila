plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.3.70" apply false
}

allprojects {
    group = "org.aquila3d"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("http://dl.bintray.com/kotlin/kotlinx.html/") }
    }
}

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"

}

group = "com.grimco.movies"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}



dependencies {

    val ktorVersion = "2.3.7"

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("org.slf4j:slf4j-simple:1.6.1")


    implementation("androidx.room:room-gradle-plugin:2.7.0-alpha04")
    ksp("androidx.room:room-compiler:2.7.0-alpha04")
    implementation("androidx.room:room-runtime-jvm:2.7.0-alpha04")
    implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha04")


}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Movies"
            packageVersion = "1.0.0"
        }
    }
}

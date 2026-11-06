plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
}

group = "io.github.nakulov"
version = "1.0.3"

dependencies {
    implementation(libs.kotest.runner.junit5)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "io.github.nakulov"
            artifactId = "kotestkit"
            version = "1.0.3"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Nakulov/KotestKit")
            credentials(PasswordCredentials::class)
        }
    }
}
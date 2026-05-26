plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
}

group = "com.simple.kotest"
version = "1.0.2"

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
            groupId = "com.simple.kotest"
            artifactId = "skotest"
            version = "1.0.2"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Nakulov/SKotest")
            credentials {
                username = providers.gradleProperty("gpr.user").get()
                password = providers.gradleProperty("gpr.key").get()
            }
        }
    }
}
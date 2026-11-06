plugins {
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
}

group = "io.github.nakulov"
version = "1.0.3"

dependencies {
    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
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

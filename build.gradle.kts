plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    `maven-publish`
}

group = "io.github.nakulov"
version = "1.0.3"

dependencies {
    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    // ReadmeSyncSpec читает эти файлы при запуске: без входов правка только в них оставляет test в UP-TO-DATE.
    inputs.file("README.md")
    inputs.dir("src/test/kotlin/io/github/nakulov/kotestkit/readme")
}

kover {
    reports {
        verify {
            rule { minBound(100) }
        }
    }
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

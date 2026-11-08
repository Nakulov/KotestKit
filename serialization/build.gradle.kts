plugins {
    // Версии плагинов задаёт корневой build.gradle.kts.
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlinx.kover")
    `maven-publish`
}

group = rootProject.group
version = rootProject.version

dependencies {
    api(project(":"))
    // Сравнение JSON в shouldEncodeTest. kotlinx-serialization-json исключён: его версию выбирает проект-потребитель.
    api(libs.kotest.assertions.json) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-json")
    }
    compileOnly(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(testFixtures(project(":")))
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
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
            artifactId = "kotestkit-serialization"
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

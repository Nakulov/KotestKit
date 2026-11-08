plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
    `java-test-fixtures`
}

// Одна версия на все артефакты: модули берут group и version отсюда.
group = "io.github.nakulov"
version = "2.1.0"

dependencies {
    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    // Пример из README для kotestkit-serialization: модуль зависит от main корня, корень от модуля только в тестах.
    testImplementation(project(":serialization"))
    testImplementation(libs.kotlinx.serialization.json)
}

java {
    withSourcesJar()
}

// Помощники тестов (src/testFixtures) нужны тестам модулей, но не потребителям.
val javaComponent = components["java"] as AdhocComponentWithVariants
javaComponent.withVariantsFromConfiguration(configurations["testFixturesApiElements"]) { skip() }
javaComponent.withVariantsFromConfiguration(configurations["testFixturesRuntimeElements"]) { skip() }

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
    currentProject {
        sources {
            excludedSourceSets.add("testFixtures")
        }
    }
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

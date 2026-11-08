import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

val kotestkitVersion = providers.gradleProperty("kotestkitVersion").get()

dependencies {
    testImplementation("io.github.nakulov:kotestkit:$kotestkitVersion")
    testImplementation("io.github.nakulov:kotestkit-serialization:$kotestkitVersion")
    testImplementation(libs.kotlinx.serialization.json)
}

// Только kotestkit-serialization, без kotlinx проекта: так видно, что приносит сам артефакт.
val serializationOnly: Configuration by configurations.creating {
    isCanBeConsumed = false
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named(TargetJvmEnvironment.STANDARD_JVM))
        attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm)
    }
}

dependencies {
    serializationOnly("io.github.nakulov:kotestkit-serialization:$kotestkitVersion")
}

// kotlinx-serialization-json выбирает проект-потребитель; -core приходит с Kotest, его не проверяем.
val checkNoKotlinxSerializationJson by tasks.registering {
    val modules = serializationOnly.incoming.resolutionResult.rootComponent.map { root ->
        root.dependencies.flatMap { collectModules(it) }.toSet()
    }
    doLast {
        val found = modules.get().filter { it.startsWith("org.jetbrains.kotlinx:kotlinx-serialization-json") }
        check(found.isEmpty()) { "kotestkit-serialization brings $found" }
    }
}

fun collectModules(
    dependency: org.gradle.api.artifacts.result.DependencyResult,
    seen: MutableSet<String> = mutableSetOf(),
): Set<String> {
    if (dependency !is org.gradle.api.artifacts.result.ResolvedDependencyResult) return seen
    val id = dependency.selected.id as? ModuleComponentIdentifier
    val key = id?.let { "${it.group}:${it.module}" } ?: dependency.selected.id.displayName
    if (seen.add(key)) dependency.selected.dependencies.forEach { collectModules(it, seen) }
    return seen
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    dependsOn(checkNoKotlinxSerializationJson)
}

plugins {
    alias(libs.plugins.kotlin.jvm)
}

val kotestkitVersion = providers.gradleProperty("kotestkitVersion").get()

dependencies {
    testImplementation("io.github.nakulov:kotestkit:$kotestkitVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

plugins {
  kotlin("jvm") version "2.2.20"
}

group = "com.simple.kotest"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(21)
}
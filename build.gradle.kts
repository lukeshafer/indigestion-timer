/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.7/samples
 */

plugins {
  `java-library`
  `maven-publish`
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

group = "dev.lksh.minecraft.plugins.timer"
version = "1.0"

java { 
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

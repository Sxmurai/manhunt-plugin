import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  kotlin("jvm") version "1.4.20"
  java
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

project.version = "1.0.0"
project.group = "xyz.aesthetical"
project.setProperty("mainClassName", "xyz.aesthetical.manhunt")

repositories {
  jcenter()
  
  maven(url = "https://hub.spigotmc.org/nexus/content/repositories/public")
}

dependencies {
  implementation(group = "org.bukkit", name = "bukkit", version = "1.15.2-R0.1-SNAPSHOT")
}

application {
  mainClass.set("xyz.aesthetical.manhunt")
}

tasks.apply {
  withType<KotlinCompile> {
    kotlinOptions.suppressWarnings = true
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
  }
  
  withType<ShadowJar> {
    manifest.attributes.apply {
      put("Main-Class", application.getMainClass())
    }
  }
}

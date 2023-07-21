plugins {
    kotlin("jvm") version "1.8.22"
    `maven-publish`
}

group = "io.github.inotia00"

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("xpp3:xpp3:1.1.4c")
    implementation("com.android.tools.smali:smali:3.0.3")
    implementation("com.google.guava:guava:32.1.1-android")
    implementation("io.github.inotia00:apktool-lib:2.7.2-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.22")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.22")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "SKIPPED", "FAILED")
        }
    }
    processResources {
        expand("projectVersion" to project.version)
    }
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(11)
}

publishing {
    repositories {
        if (System.getenv("GITHUB_ACTOR") != null)
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/revanced/revanced-patcher")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        else
            mavenLocal()
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

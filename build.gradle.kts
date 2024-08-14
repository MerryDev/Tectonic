plugins {
    id("java")
    id("java-library")
}

group = "eu.minecountry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api(project(":core"))
}

subprojects {
    apply {
        plugin<JavaPlugin>()
        plugin<JavaLibraryPlugin>()
    }

    tasks {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
            withSourcesJar()
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        // Dependencies that are already provided by another source
        compileOnly("org.jetbrains:annotations:24.1.0")

        // Dependencies that are not provided by the server
        implementation("org.apache.commons:commons-lang3:3.14.0")


        // Dependencies required for testing
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }

        compileJava {
            options.encoding = "UTF-8"
        }

        compileTestJava {
            options.encoding = "UTF-8"
        }

        javadoc {
            options.encoding = "UTF-8"
        }
    }
}
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
    }

    dependencies {
        implementation("org.jetbrains:annotations:24.1.0")
        implementation("org.apache.commons:commons-lang3:3.14.0")
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
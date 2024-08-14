rootProject.name = "tectonic"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper", "io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
        }
    }
}

include("core")
include("commands")



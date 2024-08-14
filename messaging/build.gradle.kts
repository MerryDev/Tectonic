description = "Utilities for message dispatching"

dependencies {
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")

    api(project(":core")) {
        exclude("io.papermc")
    }
    compileOnly(libs.paper)
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}
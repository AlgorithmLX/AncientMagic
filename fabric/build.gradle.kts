plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val modId: String by project
val modName: String by rootProject

architectury {
    platformSetupLoomIde()
    fabric()
}

base {
    archivesName.set("${archivesName.get()}-fabric")
}

val common: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    named("developmentFabric") { extendsFrom(common) }
}

dependencies {
    val fabricLoaderVersion: String by rootProject
    val fabricVersion: String by rootProject
    val imguiVersion: String by project
    val coroutinesVersion: String by project
    val serializationVersion: String by project

    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion") { include(this) }
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion+${project.properties["minecraftVersion"].toString()}") { include(this) }

    modImplementation("ru.hollowhorizon:HollowCore-fabric:1.21-1.0.0")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProductionFabric"))
}

tasks {
    processResources {
        val modId: String by rootProject
        from(sourceSets.main.get().resources)
        filesMatching(listOf("$modId.mixins.json", "$modId.fabric.mixins.json")) {
            expand("team._0mods.$modId.mixin" to "team._0mods.$modId.fabric.mixin")
        }
    }

    shadowJar {
        configurations = listOf(shadowBundle)
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenFabric") {
            artifactId = "${base.archivesName.get()}-fabric"
            from(components["kotlin"])
        }
    }

    repositories {
        val mk = System.getenv("MAVEN_KEY")
        val mp = System.getenv("MAVEN_PASS")
        val releaseType: String by rootProject
        val artefact = if (releaseType.isEmpty()) "releases" else "snapshots"

        logger.info("MAVEN_KEY: $mk")
        logger.info("MAVEN_PASS: $mp")

        if (mk != null && mp != null) {
            maven("https://maven.0mods.team/$artefact/") {
                credentials {
                    username = mk
                    password = mp
                }
            }
        }
    }
}

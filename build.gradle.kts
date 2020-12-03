
plugins {
    kotlin("jvm") version "1.4.20"
    `maven-publish`

    id("io.gitlab.arturbosch.detekt") version "1.14.2"
}

repositories {
    mavenCentral()
    jcenter() // required for detekt
}

configurations
    .filter { it.name.endsWith("compileClasspath", ignoreCase = true) || it.name == "detektPlugins" }
    .forEach { it.resolutionStrategy.activateDependencyLocking() }

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:latest.release")
    testImplementation("org.assertj:assertj-core:latest.release")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:latest.release")
}

group = "com.github.spartusch"
version = "2.0.0-SNAPSHOT"

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(sourcesJar)
        }
    }
}

detekt {
    buildUponDefaultConfig = true
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    jar {
        manifest.attributes(
                "Automatic-Module-Name" to "com.github.spartusch.webquery",
                "Implementation-Version" to project.version
        )
    }

    test {
        useJUnitPlatform()
        testLogging.events("started", "skipped", "failed")
    }

    register<Exec>("dockerImage") {
        val name = "${project.group}/${project.name}"
        commandLine = listOf("docker", "image", "build", "-t", "$name:${project.version}", "-t", "$name:latest", ".")
    }
}

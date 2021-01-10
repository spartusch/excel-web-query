
plugins {
    kotlin("jvm") version "1.4.21"
    `maven-publish`

    id("io.gitlab.arturbosch.detekt") version "1.15.0"
}

repositories {
    mavenCentral()
    jcenter() // required for detekt
}

dependencyLocking {
	lockAllConfigurations()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:latest.release")
    testImplementation("org.assertj:assertj-core:latest.release")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:latest.release")
}

group = "com.github.spartusch"
version = "2.0.1"

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
        testLogging.events("skipped", "failed")
    }

    register<Exec>("dockerImage") {
        val name = "${project.group}/${project.name}"
        commandLine = listOf("docker", "image", "build", "-t", "$name:${project.version}", "-t", "$name:latest", ".")
    }
}


plugins {
    kotlin("jvm") version "1.3.72"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.assertj:assertj-core:3.16.1")
}

group = "com.github.spartusch"
version = "2.0.0-SNAPSHOT"

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    repositories {
        maven { url = uri("file://$projectDir/../mvn-repo") }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(sourcesJar)
        }
    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "1.8"
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
}

import org.gradle.internal.os.OperatingSystem

plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "me.ramidzkh"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:3.2.3"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-shaderc")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    val lwjglNatives = when (OperatingSystem.current()) {
        OperatingSystem.LINUX -> System.getProperty("os.arch").let {
            if (it.startsWith("arm") || it.startsWith("aarch64"))
                "natives-linux-${if (it.contains("64") || it.startsWith("armv8")) "arm64" else "arm32"}"
            else
                "natives-linux"
        }
        OperatingSystem.MAC_OS -> "natives-macos"
        OperatingSystem.WINDOWS -> System.getProperty("os.arch").let {
            if (it.contains("64"))
                "natives-windows${if (it.startsWith("aarch64")) "-arm64" else ""}"
            else
                "natives-windows-x86"
        }
        else -> throw Error("Unrecognized or unsupported Operating system. Please set \"lwjglNatives\" manually")
    }

    testRuntimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    testRuntimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = lwjglNatives)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"

        if (JavaVersion.current().isJava9Compatible) {
            options.release.set(8)
        } else {
            sourceCompatibility = "8"
            targetCompatibility = "8"
        }
    }

    withType<Javadoc> {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            if (signing.signatory != null) {
                signing.sign(this)
            }

            pom {
                name.set("shaderc4j")
                description.set("Neater shaderc bindings for Java, using LWJGL's shaderc bindings")
                url.set("https://github.com/ramidzkh/shaderc4j")
                inceptionYear.set("2020")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("ramidzkh")
                        name.set("Ramid Khan")
                        email.set("ramidzkh@gmail.com")
                        timezone.set("Australia/Sydney")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/ramidzkh/shaderc4j.git")
                    developerConnection.set("scm:git:git@github.com:ramidzkh/shaderc4j.git")
                    url.set("https://github.com/ramidzkh/shaderc4j")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/ramidzkh/shaderc4j/issues")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("${buildDir}/repos/releases")
            val snapshotsRepoUrl = uri("${buildDir}/repos/snapshots")
            name = "Project"
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

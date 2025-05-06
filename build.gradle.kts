plugins {
    java
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.wisp.holoitemclear"
version = "1.2.2"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
}


dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

val targetJavaVersion = 16
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.jar {
    dependsOn(configurations.runtimeClasspath)
    archiveBaseName.set("HoloItemClear")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().files.map { if (it.isDirectory()) it else zipTree(it) })
}

tasks.shadowJar {
    archiveBaseName.set("HoloItemClear")
    archiveClassifier.set("")
}

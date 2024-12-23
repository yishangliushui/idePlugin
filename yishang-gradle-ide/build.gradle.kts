plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
}

group = "org.yishang"
version = "1.0"

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.3.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("Git4Idea"))
}
// runtimeOnly compileOnly implementation
dependencies {
    // MyBatis-Plus
    implementation("com.baomidou:mybatis-plus-generator:3.5.2")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.2")

    // Mapper and Generator
    implementation("tk.mybatis:mapper-generator:1.1.5")
    implementation("tk.mybatis:mapper:4.1.5")

    implementation("org.mybatis.generator:mybatis-generator-core:1.3.7")
    implementation("org.freemarker:freemarker:2.3.28")

    // MySQL Connector
    implementation("mysql:mysql-connector-java:8.0.30")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.2")

    implementation("com.alibaba:fastjson:1.2.83")

    // Swagger
    compileOnly("io.springfox:springfox-swagger2:2.8.0")

    compileOnly("com.jetbrains.intellij.platform:core-ui:233.14475.28")
    compileOnly("com.jetbrains.intellij.platform:core:233.14475.28")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }

    withType<JavaExec> {
        // 乱码
        jvmArgs = listOf("-Xmx512m", "-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}






repositories {
    mavenCentral()
    maven {
        url = uri("https://www.jetbrains.com/intellij-repository/releases/")
    }
    maven {
        url = uri("https://www.jetbrains.com/intellij-repository/snapshots/")
    }
    maven {
        url = uri("https://cache-redirector.jetbrains.com/intellij-dependencies")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}
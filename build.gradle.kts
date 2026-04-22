plugins {
    id("io.micronaut.application") version "4.6.2"
    id("com.gradleup.shadow") version "8.3.9"
    id("io.micronaut.test-resources") version "4.6.2"
    id("io.micronaut.aot") version "4.6.2"
}

version = "0.1"
group = "com.carrental"



repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.micronaut:micronaut-core-processor")
    testCompileOnly("io.micronaut:micronaut-core-processor")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.openapi:micronaut-openapi")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut.views:micronaut-views-fieldset")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")
    implementation("jakarta.validation:jakarta.validation-api")
    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("io.micronaut:micronaut-http-client")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    implementation("org.mindrot:jbcrypt:0.4")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")

}



application {
    mainClass = "com.carrental.Application"
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}




graalvmNative.toolchainDetection = false





micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.carrental.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }

}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        "-Amicronaut.openapi.enabled=true",
        "-Amicronaut.openapi.views.spec=swagger-ui.enabled=true"
    ))
}

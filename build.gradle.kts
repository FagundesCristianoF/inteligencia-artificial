import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.0"
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

val mainSourceSet = the<SourceSetContainer>()["main"]

tasks.register<JavaExec>("runBucket") {
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("bucket.MainKt")
}

tasks.register<JavaExec>("runHanoi") {
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("hanoi.MainKt")
}

tasks.register<JavaExec>("runTicTacToe") {
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("tictactoe.MainKt")
}

tasks.register<JavaExec>("runKnn") {
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("knn.KNN")
}

tasks.register<JavaExec>("runKmeans") {
    classpath = mainSourceSet.runtimeClasspath
    mainClass.set("kmeans.KMeans")
}

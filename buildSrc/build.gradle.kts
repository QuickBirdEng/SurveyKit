

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:3.4.1")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
}

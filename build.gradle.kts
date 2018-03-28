import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories

buildscript {
    extra["kotlin_version"] = "1.2.30"

    dependencies {
        classpath("com.android.tools.build:gradle:3.0.0-beta2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlin_version"]}")
        classpath(kotlin("gradle-plugin"))
    }
    repositories {
        jcenter()
        mavenCentral()
        google()
    }
}

apply {
    //    plugin<AppPlugin>()
    //    plugin<KotlinAndroidPluginWrapper>()
    plugin("com.android.application")
    plugin("kotlin-android")
    plugin("kotlin-android-extensions")
}

//android {
//    buildToolsVersion("25.0.0")
//    compileSdkVersion(23)
//
//    defaultConfig {
//        minSdkVersion(15)
//        targetSdkVersion(23)
//
//        applicationId = "com.example.kotlingradle"
//        versionCode = 1
//        versionName = "1.0"
//    }
//
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//            proguardFiles("proguard-rules.pro")
//        }
//    }
//
//    sourceSets {
//        main {
//            res.srcDirs = ['src/main/kotlin']
//        }
//    }
//}
//
//dependencies {
//    compile("com.android.support:appcompat-v7:23.4.0")
//    compile("com.android.support.constraint:constraint-layout:1.0.0-alpha8")
//    compile(kotlin("stdlib"))
//    testCompile("junit", "junit", "4.12")
//}

repositories {
    jcenter()
    mavenCentral()
    google()
}
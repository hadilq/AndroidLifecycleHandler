/**
 * Copyright 2020 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.github.hadilq.build.plugin.BuildPlugin.Companion.KOTLIN_STDLIB
import com.github.hadilq.build.plugin.BuildPlugin.Companion.VERSION_COMPILE_SDK
import com.github.hadilq.build.plugin.BuildPlugin.Companion.LIFECYCLE
import com.github.hadilq.build.plugin.BuildPlugin.Companion.JUNIT
import com.github.hadilq.build.plugin.BuildPlugin.Companion.LIFECYCLE_COMPILER
import com.github.hadilq.build.plugin.BuildPlugin.Companion.MOCKITO
import com.github.hadilq.build.plugin.BuildPlugin.Companion.ROBOLECTRIC
import com.github.hadilq.build.plugin.BuildPlugin.Companion.VERSION_JACOCO
import com.github.hadilq.build.plugin.BuildPlugin.Companion.VERSION_MIN_SDK
import com.github.hadilq.build.plugin.BuildPlugin.Companion.VERSION_TARGET_SDK

plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("com.vanniktech.android.junit.jacoco") version "0.16.0"
  id("org.jetbrains.dokka") version "1.4.0-rc"
  id("com.github.hadilq.build-plugin")
}

android {
  compileSdkVersion(VERSION_COMPILE_SDK)
  defaultConfig {
    minSdkVersion(VERSION_MIN_SDK)
    targetSdkVersion(VERSION_TARGET_SDK)
  }

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

dependencies {
  kapt(LIFECYCLE_COMPILER)

  implementation(kotlin(KOTLIN_STDLIB))
  implementation(LIFECYCLE)

  testImplementation(JUNIT)
  testImplementation(MOCKITO)
  testImplementation(ROBOLECTRIC)
}

tasks.dokkaHtml {
  outputDirectory = "$buildDir/dokka"
  dokkaSourceSets {
    create("main") {
      noAndroidSdkLink = true
    }
  }
}

//if (project.hasProperty("signing.keyId")) {
//  apply from: '../buildSystem/deploy.gradle'
//}

junitJacoco {
  jacocoVersion = VERSION_JACOCO
}

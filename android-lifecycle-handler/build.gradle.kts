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
import com.github.hadilq.build.plugin.JUNIT
import com.github.hadilq.build.plugin.KOTLIN_STDLIB
import com.github.hadilq.build.plugin.KOTLIN_TEST_ANNOTATIONS_COMMON
import com.github.hadilq.build.plugin.KOTLIN_TEST_COMMON
import com.github.hadilq.build.plugin.LIFECYCLE
import com.github.hadilq.build.plugin.MOCKITO
import com.github.hadilq.build.plugin.MOCKK_COMMON
import com.github.hadilq.build.plugin.ROBOLECTRIC
import com.github.hadilq.build.plugin.VERSION_COMPILE_SDK
import com.github.hadilq.build.plugin.VERSION_MIN_SDK
import com.github.hadilq.build.plugin.VERSION_TARGET_SDK
import com.github.hadilq.build.plugin.setupJacoco
import com.github.hadilq.build.plugin.setupPublication

plugins {
  id("kotlin-multiplatform")
  id("com.android.library")
  id("com.github.hadilq.build-plugin")
}

android {
  compileSdkVersion(VERSION_COMPILE_SDK)

  defaultConfig {
    targetSdkVersion(VERSION_TARGET_SDK)
    minSdkVersion(VERSION_MIN_SDK)
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }
}

kotlin {
  android {
    publishLibraryVariants = listOf("release")
  }
  jvm {
    compilations.all {
      kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
      }
    }
  }

  sourceSets {
    commonMain {
      dependencies {
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin(KOTLIN_TEST_COMMON))
        implementation(kotlin(KOTLIN_TEST_ANNOTATIONS_COMMON))
        implementation(MOCKK_COMMON)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(kotlin(KOTLIN_STDLIB))
        implementation(LIFECYCLE)
      }
    }

    val androidTest by getting {
      dependencies {
        implementation(JUNIT)
        implementation(MOCKITO)
        implementation(ROBOLECTRIC)
      }
    }
  }

}

tasks.withType<GenerateModuleMetadata> {
  enabled = false
}

setupJacoco()
setupPublication()

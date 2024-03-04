@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.example.dikidi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dikidi"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.core)
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature-root"))
    implementation(project(":feature-home"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.accompanist.insets)
    implementation(libs.androidx.ui.util)

    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    implementation(libs.coil.compose)

    implementation (libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.core)
    implementation(libs.mvikotlin.coroutines)

    implementation(libs.decompose.core)
    implementation(libs.decompose.jetpack)
}
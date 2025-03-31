plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.refactorme.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.refactorme.demo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    val flavorDimension = "main"
    flavorDimensions += flavorDimension
    productFlavors {
        create("staging") {
            dimension = flavorDimension
            applicationIdSuffix = ".staging"
            buildConfigField("String", "API_SERVER", "\"https://restcountries.com\"")
        }
        create("production") {
            dimension = flavorDimension
            buildConfigField("String", "API_SERVER", "\"https://restcountries.com\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.kt.coroutines.core)
    implementation(libs.kt.coroutines.android)
    implementation(libs.kt.coroutines.test)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
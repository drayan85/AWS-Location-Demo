plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias (libs.plugins.ksp)
    alias (libs.plugins.hilt)
}

android {
    namespace = "com.aws.demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aws.demo"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

configurations.configureEach {
    resolutionStrategy {
        force(libs.okhttp)
    }
    exclude(group = "com.squareup.okhttp3", module = "okhttp-coroutines")
}

dependencies {

    ksp(libs.hilt.compiler)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)

    implementation (libs.dagger)
    implementation (libs.dagger.android.support)
    implementation (libs.hilt.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.aws.location)
    implementation(libs.aws.auth)
    implementation(libs.aws.http.client)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
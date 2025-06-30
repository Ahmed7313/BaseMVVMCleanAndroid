plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.basemvvmcleanandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.basemvvmcleanandroid"
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.compose.icons)

    // Jetpack Compose Libraries
//    implementation(libs.compose.ui)
//    implementation(libs.compose.material)
//    implementation(libs.compose.activity)
//    implementation(libs.compose.icons)
//    implementation(libs.compose.ui.tooling)
//    implementation(libs.compose.foundation)
    implementation(libs.androidx.navigation.compose.v276)


    // Hilt & Dagger - Dependency Injection
    implementation(libs.dagger.core)
    implementation(libs.dagger.lint.aar)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.navigation.compose)

    // Retrofit, OkHttp, and Moshi
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okHttp)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.scalarsConverter)
    implementation(libs.moshiKotlin)
    implementation(libs.moshiConverter)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.symbol.processing.api)

    //Compose Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Timber for logging
    implementation(libs.timber)

    //Lottie
    implementation(libs.lottie.compose)

    // Room dependencies
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //QrCode zxing
    implementation(libs.zxing.android.embedded)
    implementation(libs.zxing.core)

    //coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    //Sweet Toast
    implementation("com.github.TheHasnatBD:SweetToast:1.0.2") {
        exclude(group = "com.android.support")
    }
}
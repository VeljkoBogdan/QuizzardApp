plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.veljkobogdan.quizzardapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.veljkobogdan.quizzardapp"
        minSdk = 27
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.flexbox)

    // The view calendar library for Android
    implementation("com.kizitonwose.calendar:view:2.6.1")

    // The compose calendar library for Android
    implementation("com.kizitonwose.calendar:compose:2.6.1")

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
}
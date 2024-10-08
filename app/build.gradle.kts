plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.veljkobogdan.quizzardapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.veljkobogdan.quizzardapp"
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

    val fragmentVersion = "1.5.7"
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")

    val roomVersion = "2.6.1"
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:$roomVersion")
    //noinspection UseTomlInstead
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    implementation(libs.gson)
}
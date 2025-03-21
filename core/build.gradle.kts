plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.coding.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        viewBinding = true
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", "API_KEY", "\"d3f3c8cdeeed43ce9afc62163015968b\"")

    }

}

dependencies {
    // General Dependencies
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.recyclerview)
    api(libs.glide)

    // Room Database
    api(libs.room.runtime)
    ksp(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // Networking
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.logging.interceptor)

    // Coroutines & Lifecycle
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
    api(libs.androidx.room.ktx)
    api(libs.androidx.lifecycle.livedata.ktx)

    // Navigation
    api(libs.androidx.navigation.runtime.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.fragment)

    // Preferences
    api(libs.androidx.datastore.preferences)

    // Dependency Injection
    api(libs.koin.android)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // database encryption
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}

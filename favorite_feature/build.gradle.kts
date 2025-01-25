plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
}


android {
    namespace = "com.coding.favorite_feature"
    compileSdk = 34

    defaultConfig {

        minSdk = 24

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(project(":core"))

    dependencies {
        implementation(libs.androidx.navigation.runtime.ktx)
        implementation(libs.androidx.navigation.fragment.ktx)
    }


}
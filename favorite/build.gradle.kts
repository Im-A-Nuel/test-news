plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.coding.favorite_feature"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
}

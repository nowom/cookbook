plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "nowowiejski.michal.recipes"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(":core:design-system"))

    val nav_version = "2.7.5"
    val koin_android_version = "3.5.0"
    val lifecycle_version = "2.6.2"
    implementation(project(":core:model"))
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("io.insert-koin:koin-android:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_android_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
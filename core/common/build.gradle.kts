plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "nowowiejski.michal.domain"
    compileSdk = 33
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val koinAndroidVersion = "3.5.0"
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC2")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
}
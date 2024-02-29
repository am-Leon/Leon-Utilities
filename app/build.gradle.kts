plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "am.leon.leonutilit"
    compileSdk = 34

    defaultConfig {
        applicationId = "am.leon.leonutilit"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

//    applicationVariants.configureEach { variant ->
//        variant.outputs.all { output ->
//            val versionName = defaultConfig.versionName
//            val buildType = variant.buildType.name
//            // Output name
//            outputFileName = "Leon-utilities-${versionName}-${buildType}.apk"
//        }
//    }
}

dependencies {
    // Unit Testing
    testImplementation("junit:junit:4.13.2")

    // Instrumented Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ---------------------------------------------------------------------------------------------

    // Android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Kotlin
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")

    // Google Gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(project(":leon-utilities"))
}
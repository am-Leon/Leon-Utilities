plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
//    id 'maven-publish'
}

//ext {
//    mGroupId = "am-leom"
//    mArtifactId = "utilities"
//    mVersionCode = 13
//    mVersionName = "v1.1.5"
//
//    mLibraryName = "Leon-Utilities"
//    mLibraryDescription = "$mLibraryName Contains Utilities classses."
//}

android {
    namespace = "am.leon.utilities"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }

//    libraryVariants.configureEach { variant ->
//        variant.outputs.all { output ->
//            // Output name
//            outputFileName = "leon-utilities-${mVersionName}-release.aar"
//        }
//    }
//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
//    }
}

dependencies {
    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.5")
    testImplementation("app.cash.turbine:turbine:1.0.0")

    // Instrumented Testing
    androidTestImplementation("com.google.truth:truth:1.1.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("app.cash.turbine:turbine:1.0.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ---------------------------------------------------------------------------------------------

    // Android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("com.google.android.material:material:1.10.0")

    // Kotlin
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

    // Google Gson
    implementation("com.google.code.gson:gson:2.10.1")
}

//afterEvaluate {
//    publishing {
//        publications {
//            maven(MavenPublication) {
//                groupId = mGroupId
//                artifactId = mArtifactId
//                version = mVersionName
//
//                from components.release
//
//                pom {
//                    name = mLibraryName
//                    description = mLibraryDescription
//                }
//            }
//        }
//
//        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = uri("https://maven.pkg.github.com/am-Leon/Leon-Utilities")
//            }
//        }
//    }
//}
//
//// Assembling should be performed before publishing package
//publish.dependsOn assemble
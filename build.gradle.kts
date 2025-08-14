


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
     id("com.google.gms.google-services")
}

android {
    namespace = "com.example.carparking"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.bharat_parking_system"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    dependencies {
        // Firebase BOM (Bill of Materials)
        implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

        // Firebase Libraries (corrected)
        implementation("com.google.firebase:firebase-auth-ktx")
        implementation("com.google.firebase:firebase-database-ktx") // Fixed database reference
        implementation("com.google.firebase:firebase-firestore-ktx") // Corrected firestore

        // AndroidX
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        // Testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.google.gms.google-services")
}


android {
    namespace = "app.waste2wealth.com"
    compileSdk = 33

    defaultConfig {
        applicationId = "app.waste2wealth.com"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    //Compose Bom
    implementation(platform(libs.compose.bom))

    //Compose
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    //Navigation Compose
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.navigation)

    // Lottie Animation
    implementation(libs.lottie)


    //ViewModel Compose
    implementation(libs.viewmodel.compose)

    //SystemUIController
    implementation(libs.system.uicontroller)

    //Coil
    implementation(libs.coilx)

    //Permissions
    implementation(libs.permissions)

    // Location Services
    implementation(libs.location.services)

    //Material Extended
    implementation(libs.material.icons.extended)
    implementation("io.github.vanpra.compose-material-dialogs:core:0.9.0")

    // Camera
    implementation(libs.camera.android)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    kapt(libs.dagger.hilt.kapt)
    implementation(libs.dagger.hilt.navigation)

    //Firebase
    implementation(libs.firebase.bom)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.jet.firestore)
    implementation(libs.firebase.auth.ktx)

    //Stacked Cards
    implementation(libs.stacked.cards)


    implementation(libs.camera.core)
    implementation(libs.camera.camera2)

    //Barcode
    implementation(libs.google.mlkit)

}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.sonara"
    compileSdk = 34 // RETORNADO PARA 34: Compatibilidade total com o AGP 8.5.2

    defaultConfig {
        applicationId = "com.example.sonara"
        minSdk = 26
        targetSdk = 34 // RETORNADO PARA 34
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Kotlin 1.9.24 casa perfeitamente com o Compiler do Compose 1.5.14
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core e utilitários
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.exifinterface)

    // Ciclo de vida (Lifecycle)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Jetpack Compose & BOM
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.animation)

    // Material 3 e Ícones
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // Navegação e Room
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.ktx)

    // Carregamento de Imagens Assíncronas
    implementation(libs.coil.compose)
    implementation(libs.coil.base)

    // Hilt (Injeção de dependência) - CORRIGIDO: Removido o foundation intruso daqui
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // GSON, Network e DataStore
    implementation(libs.gson)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.okhttp.logging.interceptor)

    // Testes e Debug
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Forçador de Segurança para barrar qualquer sub-dependência transitiva intrusa de fora
configurations.all {
    resolutionStrategy {
        force(
            "androidx.core:core:1.13.1",
            "androidx.core:core-ktx:1.13.1",
            "androidx.lifecycle:lifecycle-runtime-compose:2.8.4",
            "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4"
        )
    }
}
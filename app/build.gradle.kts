plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    compileSdk = Config.compileSdk
    buildToolsVersion = Config.BUILD_TOOLS_VERSION

    defaultConfig {
        /*  configurations.all {
              resolutionStrategy { force 'androidx.core:core-ktx:1.6.0' }
          }*/
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        testInstrumentationRunner = Config.ANDROID_JUNIT_RUNNER

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf("room.schemaLocation" to "$projectDir/schemes")
                )
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Config.JAVA_VERSION
        targetCompatibility = Config.JAVA_VERSION
    }

    viewBinding{
        android.buildFeatures.viewBinding = true
    }

    kotlinOptions {
        jvmTarget = Config.JVM_TARGET
    }
}

dependencies {
    // Android
   implementation (Android.AppCompat)

    // Kotlin
    implementation (Android.CoreKtx)
    implementation (Android.KotlinStdlib)

    // UI
    implementation (Android.Material)
    implementation (Android.ConstrainLayout)

    //    // Tests
    testImplementation (Tests.JUnit)
    androidTestImplementation (Tests.AndroidJUnit)
    androidTestImplementation(Tests.Espresso)

    // retrofit
    implementation(Retrofit.Retrofit)
    implementation(Retrofit.ConverterGson)
    implementation (Retrofit.RetrofitCoroutine)
    implementation (Retrofit.RxJavaAdapter)
    implementation (Retrofit.LoginInterceptor)
    
    //    // Koin
    implementation (Koin.Core)
    implementation (Koin.Android)
    implementation (Koin.AndroidCompat)

    // Coroutines
    implementation (Kotlin.CoroutinesCore)
    implementation (Kotlin.CoroutinesAndroid)
    implementation (Kotlin.CoroutinesAdapter)

    // Room
    implementation (Room.Core)
    kapt (Room.Compiler)
    implementation (Room.Ktx)

    // Coil
    implementation (Coil.Coil)
}

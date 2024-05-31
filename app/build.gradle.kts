plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.miniproyecto2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.miniproyecto2"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    val navVersion = "2.3.5"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation ("org.mockito:mockito-android:3.11.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    debugImplementation ("org.jacoco:org.jacoco.core:0.8.7")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-common:$navVersion")

    //CardView
    implementation("androidx.cardview:cardview:1.0.0")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    //Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Room
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")

    //Boton flotante
    implementation ("com.getbase:floatingactionbutton:1.10.1")

    //Retrofit (Consumo de API's)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide (Consumir imagenes API)
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    //Toolbar
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")

    //Dagger_Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-firestore")

    //Firebase authentication
    implementation("com.google.firebase:firebase-auth-ktx")

}
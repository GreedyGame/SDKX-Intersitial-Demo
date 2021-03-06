# SDKX-Interstitial-Demo-kt
This demo shows how to integrate Interstitial Ads into your App using SDKX.

SDKX Version  ![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.greedygame.sdkx/core/badge.svg)

# Integration
Apps can easily integrate GreedyGame SDKX with Gradle
#### Add the dependency to your app `build.gradle`
``` gradle
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    ...............
    ...............
    //greedygame sdk
     implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
     implementation 'androidx.multidex:multidex:2.0.1'
     implementation "androidx.palette:palette:1.0.0"
     implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
     kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")
     implementation 'com.greedygame.sdkx:core:x.y.z'
}
```

Note: `GGInterstitialAd` no longer refreshes automatically once close starting with v0.0.86. GGInterstialAd will behave similar to Admob intersitial. If you still need the auto refresh feature, use `GGAutoRefreshInterstitialAd`

# License
The code for the sample app is provided under MIT License.

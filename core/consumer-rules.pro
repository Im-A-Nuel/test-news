# Keep all model classes in the app module
-keep class com.coding.manewsapp.model.** { *; }

# Keep Retrofit API interfaces
-keep interface com.coding.manewsapp.network.** { *; }

# Keep ViewModels (if using Jetpack ViewModel)
-keep class com.coding.manewsapp.ui.viewmodel.** { *; }

# Prevent obfuscation of Parcelable classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep JSON model classes (if using Gson)
-keep class com.google.gson.** { *; }
-keep class com.coding.manewsapp.model.** { *; }

# Keep navigation components (Safe Args)
-keepclassmembers class * {
    @androidx.navigation.* <fields>;
}

# Keep WorkManager classes
-keep class androidx.work.** { *; }

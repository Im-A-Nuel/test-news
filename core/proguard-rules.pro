# Keep all model classes in core module
-keep class com.coding.manewsapp.core.model.** { *; }

# Keep Room database entities
-keep class androidx.room.** { *; }
-keep class com.coding.manewsapp.core.database.** { *; }

# Keep Retrofit API interfaces
-keep interface com.coding.manewsapp.core.network.** { *; }

# Keep Gson parsing models
-keep class com.google.gson.** { *; }
-keep class com.coding.manewsapp.core.model.** { *; }

# Prevent obfuscation of Retrofit/OkHttp classes
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Keep WorkManager classes (if used in core)
-keep class androidx.work.** { *; }

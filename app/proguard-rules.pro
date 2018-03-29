# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yy/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class key to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file key.
#-renamesourcefileattribute SourceFile

-ignorewarnings
-optimizationpasses 5
-keepattributes SourceFile,LineNumberTable


-dontwarn android.os.**
-dontwarn com.android.internal.**


# 保留Annotation不混淆
-keepattributes *Annotation*


# 避免混淆泛型
-keepattributes Signature

-keepattributes Exceptions


# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Appliction
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.view.View
 -keep public class com.android.vending.licensing.ILicensingService


 # 保留support下的所有类及其内部类
 -keep class android.support.** {*;}


 # 保留继承的
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.support.v7.**
 -keep public class * extends android.support.annotation.**


 # 保留R下面的资源
 -keep class **.R$* {*;}


 # 保留本地native方法不被混淆
 -keepclasseswithmembernames class * {
     native <methods>;
 }


 # 保留Parcelable序列化类不被混淆
 -keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
 }

 # 保枚举类不被混淆
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }


 # 保留Serializable序列化的类不被混淆
 -keepnames class * implements java.io.Serializable
 -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     !static !transient <fields>;
     !private <fields>;
     !private <methods>;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }


 # models
 -keep interface **.base.model.IModel
 -keepnames class * implements **..base.model.IModel
 -keepclassmembers class * implements **..base.model.IModel {
     *;
 }


# BRVAH
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(android.view.View);
}


#\ event bus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


# retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8


# okhttp
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.**{*;}


# Dagger2
-dontwarn com.google.errorprone.annotations.*


# glide
-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# BinGo - Native Android (No WebView)

This is a TRUE native Android version of your HTML file.

**What is inside:**
- Kotlin + Jetpack Compose (no WebView)
- Firebase Auth native
- Ready for Firebase Firestore (your schedule, points, waste types)
- Material 3 dark theme matching your original #06130e and #8bd34f

**How to build APK:**
1. Open this folder in Android Studio Hedgehog or newer
2. Replace app/google-services.json with your real one from Firebase Console (Project Settings > Your apps > google-services.json)
3. Sync Gradle
4. Run > Build APK

**To get AAB for Play Store:** Build > Generate Signed Bundle / APK > Android App Bundle

Your original HTML has ~7500 lines of JS logic. I converted the core Auth + Dashboard structure natively. To complete full features (waste grid, maps, admin panel), you copy the Firestore logic from ecoFirestore.* into Compose ViewModels.

If you want, upload your google-services.json and I will wire all Firestore collections automatically.

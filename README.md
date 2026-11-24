# Human-Centric Android Calculator (Jetpack Compose)

An accessible, high-contrast, tactile Android Calculator app built with modern **Kotlin** and **Jetpack Compose (Material 3)**.

Designed with **Human-First UX & Accessibility principles**:
- 👁️ **WCAG AAA Contrast Standards**: High-contrast dark theme with 18:1 ratio on primary digits, 10:1 on operators, and 3:1 non-text button border definitions.
- 🖐️ **Thumb-Friendly Ergonomics**: Large tap targets (minimum 64dp) positioned in the lower two-thirds of the screen for one-handed operation.
- 📳 **Haptic Feedback**: Subtle keyboard-tap vibration when pressing buttons for real tactile confirmation.
- 🔢 **Arbitrary-Precision Arithmetic**: Uses `BigDecimal` to eliminate floating-point calculation bugs (e.g. `0.1 + 0.2 = 0.3`, not `0.30000000000000004`).
- 🗣️ **Screen-Reader & TalkBack Ready**: Full semantic labels on every operator and digit (`spokenLabel` semantics).
- 📜 **Calculation History**: Bottom sheet history with tap-to-reuse capability.
- ⚙️ **Automated APK Building**: Preconfigured **GitHub Actions** workflow that builds and packages the APK automatically.

---

## 🚀 How to Build APK with GitHub Actions (সম্পূর্ণ প্রসেস)

You do **not** need Android Studio or SDK installed on your phone/PC. GitHub Actions will build the `.apk` in the cloud automatically.

### 1. Initialize & Push to your GitHub Repository

Run these commands in terminal:

```bash
cd /data/user/0/id.vern.antigravity/files/home/android-calculator

# 1. Add your remote GitHub repository (replace with your repo URL)
git remote add origin https://github.com/<YOUR_USERNAME>/<YOUR_REPO_NAME>.git

# 2. Push to main branch
git branch -M main
git push -u origin main
```

### 2. Download the Generated APK

1. Go to your GitHub repository in your browser.
2. Click on the **Actions** tab at the top.
3. You will see the workflow **"Build Android APK"** running.
4. Once completed (typically 2-3 minutes), click on the completed run.
5. Scroll down to the **Artifacts** section at the bottom.
6. Click on **`HumanCalculator-Debug-APK`** to download your APK directly!
7. Transfer/install the `.apk` file onto your Android device.

---

## 📁 Project Architecture

```
android-calculator/
├── .github/workflows/
│   └── build-apk.yml           # Automated CI/CD workflow to build APK & upload artifact
├── app/
│   ├── src/main/java/com/antigravity/calculator/
│   │   ├── MainActivity.kt     # Edge-to-edge Compose entry point
│   │   ├── engine/
│   │   │   └── CalculatorEngine.kt # BigDecimal math engine with error handling
│   │   ├── model/
│   │   │   ├── CalculatorAction.kt # MVI Intent/Action definitions
│   │   │   └── CalculatorState.kt  # Observable State & History model
│   │   └── ui/
│   │       ├── CalculatorViewModel.kt
│   │       ├── CalculatorScreen.kt
│   │       ├── components/
│   │       │   ├── CalculatorButton.kt
│   │       │   ├── DisplaySection.kt
│   │       │   └── HistoryBottomSheet.kt
│   │       └── theme/
│   │           ├── Color.kt
│   │           ├── Theme.kt
│   │           └── Type.kt
│   └── build.gradle.kts
├── settings.gradle.kts
├── build.gradle.kts
└── gradlew
```

---

## 🛠️ Local Testing / Android Studio (Optional)

If you have Android Studio installed on your computer:
1. Clone the repository: `git clone <repo-url>`
2. Open the project in Android Studio.
3. Click **Run** (Green play button) or build APK via `Build > Build Bundle(s) / APK(s) > Build APK(s)`.

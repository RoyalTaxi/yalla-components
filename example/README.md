# Yalla Components Example App

Small Android host app for validating `yalla-components` before publishing.

## What it shows

- `BrandServiceItem`
- `SupportButton`

## Run

From project root:

```bash
./gradlew :example:assembleDebug -Dorg.gradle.jvmargs='-Xmx2048m -XX:MaxMetaspaceSize=768m'
```

APK output:

`example/build/outputs/apk/debug/example-debug.apk`

## Preview in Android Studio

Open:

`example/src/main/kotlin/uz/yalla/components/example/MainActivity.kt`

Then use `ExampleScreenPreview`.

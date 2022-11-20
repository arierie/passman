# Password Manager
Simple app to demonstrate Rust language integration in Android project. The idea of the app is to manage password (hence the name), but for this initial implementation, we just use it to generate random password.

### Overview
The idea is to plug native code written in Rust to Android project. Android supports this kind of integration through NDK (Native Development Kit). We usually found an Android app uses NDK for C++ integration to handle low-level computation under the hood. This time, we try to use Rust instead, a new language that gain popularity in the recent years in the developers world. Rust is a statically-typed programming language designed for performance and safety, especially safe concurrency and memory management. According to  [the Stack Overflow Developer Survey 2021](https://insights.stackoverflow.com/survey/2021)  [](https://insights.stackoverflow.com/survey/2020#technology-most-loved-dreaded-and-wanted-languages-loved) conducted among over 80,000 developers,  **Rust is the most beloved programming language.** All the android code are available on the `app` module, just like a normal app. But here, on the same level, we add another directory for the Rust code, here I name it `rust`.

### Installation
Normally, the integration is quite complicated and take a bit of time, especially for the first timer like me. But, thanks to [Mozilla rust-android-gradle plugin](https://github.com/mozilla/rust-android-gradle) that can help us to skip so many manual process.

#### Prerequisites
To run this app, make sure you have NDK installed. Simply go to `Android Studio > Preferences > Appearance & Behaviour > Android SDK > SDK Tools`. Check the following options for installation and click `Apply > Ok`.
```
* Android SDK Platform Tools
* Android SDK Command-line Tools
* NDK
* CMake
* LLDB
```
**Notes**: LLDB might not be available in some version of Android studio, so, just make sure to select the rest of the options.

#### Building Rust code
The communication between Rust and Android code is happening via JNI (Java Native Interface), basically the app will generate libraries to the Android `jniLibs` source-sets containing binding to the code written in Rust. That means, live changes is not possible at the moment, we need to recompile Rust whenever we make any changes to the Rust code in order for JNI to regenerate the necessary libraries. To compile the Rust code, we can use this command below from the [rust-android-gradle plugin](https://github.com/mozilla/rust-android-gradle):
```groovy
./gradlew cargoBuild
```

#### Running Android app
After compilation is done, then we can run our app by pressing the run button or simply using this gradle command-line:
```groovy
./gradlew installDebug
```
### Screenshot
This is a very simple app with basic compose component to click and show the generated password.

<img src="img/screenshot.png" width="300"/>

### Important links
- [rust-android-gradle plugin](https://github.com/mozilla/rust-android-gradle)
- [Building and Deploying a Rust library on Android](https://mozilla.github.io/firefox-browser-architecture/experiments/2017-09-21-rust-on-android.html)

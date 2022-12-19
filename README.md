# AtomeMerchantDemo-Android

Atome Merchant Android Demo 

## Integration

- If your project needs Java implementation, please see [Java version](readme/README-java.md).

## Setup

Add the dependency in your `build.gradle` file. 

```groovy
implementation 'io.github.atome-fe:merchant-sdk:1.2.0'
```

## Usage

1. In your application code, Init the Atome SDK.

```kotlin
//Use kotlin language call
AtomeSDK.init(applicationContext)
```
```java
// Use java language call
AtomeSDK.INSTANCE.init(applicationContext)
```
2. Check whether the atome APP is installed.

```kotlin
//Use kotlin language call
AtomeSDK.isInstalledAtomeApp()
```
```java
// Use java language call
AtomeSDK.INSTANCE.isInstalledAtomeApp()
```
3. Provide a payment url to SDK.

```kotlin
//Use kotlin language call
AtomeSDK.setPaymentUrl(url)
```

```java
//Use java language call
AtomeSDK.INSTANCE.setPaymentUrl(url)
```
##

If you need call the take picture feature in web page

1. Create a TakePictureLauncher object in your Webview page.

```kotlin
 val takePictureLauncher : TakePictureLauncher = TakePictureLauncher.create(this)
```

2. Override the onShowFileChooser method of WebChromeClient, use the TakePictureLauncher proxy it.

```kotlin

  webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                return takePictureLauncher.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }
        
```

## Notes

Dependent libraries and versions used by the SDK

```kotlin 
// kotlin
"org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0"
'androidx.activity:activity-ktx:1.3.1'
```

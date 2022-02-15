# AtomeMerchantDemo-Android

Atome Merchant Android Demo 

## Integration

## Setup

Add the dependency in your `build.gradle` file. 

```groovy
implementation 'io.github.atome-fe:merchant-sdk:1.1.1'
```

## Usage

1. In your application code, Init the Atome SDK.

```kotlin
AtomeSDK.init(application)
```
2. Check whether the atome APP is installed.

```kotlin
AtomeSDK.isInstalledAtomeApp()
```
3. Provide a payment url to SDK.

```kotlin
AtomeSDK.setPaymentUrl(url)
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

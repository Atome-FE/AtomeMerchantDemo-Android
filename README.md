# AtomeMerchantDemo-Android

Atome Merchant Android Demo 

## Integration

## Setup

Add the dependency in your `build.gradle` file. 

```groovy
implementation 'com.atome.sdk:atome-merchant-sdk:1.0.0'
```

## Usage

In your application code, Init the Atome SDK.

```kotlin
AtomeSDK.init(application)
```

Provide a payment url to SDK.

```kotlin
AtomeSDK.setPaymentUrl(url)
```

## Notes

Dependent libraries and versions used by the SDK

```kotlin 
// kotlin
"org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10"
```

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


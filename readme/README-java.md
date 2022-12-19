# AtomeMerchantDemo-Android

Atome Merchant Android Demo 

## Integration

## Setup

Add the dependency in your `build.gradle` file. 

```groovy
implementation 'io.github.atome-fe:merchant-sdk-java:1.2.0'
```

## Usage

1. In your application code, Init the Atome SDK.


```java
// Use java language call
AtomeSDK.getInstance().init(applicationContext);
```
2. Check whether the atome APP is installed.

```java
// Use java language call
AtomeSDK.getInstance().isInstalledAtomeApp();
```
3. Provide a payment url to SDK.

```java
//Use java language call
AtomeSDK.getInstance().handleUrl(url);
```
##

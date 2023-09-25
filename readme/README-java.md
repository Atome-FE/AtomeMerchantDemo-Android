# AtomeMerchantDemo-Android

Atome Merchant Android Demo 

## Integration

## Setup

Add the dependency in your `build.gradle` file. 

```groovy
implementation 'io.github.atome-fe:merchant-sdk-java:1.5.0'
```

## Usage

1. In your application code, Init the Atome SDK.


```java
AtomeSDK.getInstance().init(applicationContext);
```
2. Check whether the atome APP is installed.

```java
AtomeSDK.getInstance().isAtomeInstalled();
```
3. Provide a payment url to SDK.

```java
AtomeSDK.getInstance().handleUrl(url);
```
##

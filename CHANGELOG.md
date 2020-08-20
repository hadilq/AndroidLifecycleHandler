Changelog
=========

0.4.0
-----

_2020-08-20_

 - Move to Kotlin Multiplatform
  + The previous artifactId was `androidlifecyclehandler` which is now as following:
     * For Android, it's `android-lifecycle-handler-android`
     * For Jvm, it's `android-lifecycle-handler-jvm`
 - Remove `buildSrc`, instead use composite build with `build-plugin`
 - Move `Life`, `LifeFactory`, `LifeHandler`, and `Slife`
 - Create `DLife` to extend `ELife` from it
 - Configure JaCoCo
 - Configure Maven Publication
 - Configure Dokka


0.3.0
-----

_2020-04-08_

Renaming:
 - `AndroidExtendedLifecycleHandlerImpl` to `AndroidELifecHandlerImpl`
 - `AndroidExtendedLifecycleHandler` to `AndroidELifecHandler`
 - `AndroidLifecycleHandlerImpl` to `AndroidLifeHandlerImpl`
 - `AndroidLifecycleHandler` to `AndroidLifeHandler`
 - `BaseLifecycleHandler` to `BaseLifeHandler`
 - `ExtendedLife` to `ELife`

Adding:
 - `LifeSpan#CONFIGURATION_CHANGED`
 - `LifeSpan#USER_FLOW`
 - `LifeStore`
 - `SLife`

0.2.0
-----

_2020-02-02_

Implement `AndroidLifecycleHandlerImpl` and `AndroidExtendedLifecycleHandlerImpl`. Also make the 
`ExtendedLife#onBorn` method accepts null bundle. 

0.1.0
-----

_2020-01-15_

Initial release 

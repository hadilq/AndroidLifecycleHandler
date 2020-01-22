[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/androidlifecyclehandler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/androidlifecyclehandler)
[![CircleCI](https://circleci.com/gh/hadilq/AndroidLifecycleHandler.svg?style=svg)](https://circleci.com/gh/hadilq/AndroidLifecycleHandler)
[![codecov](https://codecov.io/gh/hadilq/AndroidLifecycleHandler/branch/master/graph/badge.svg)](https://codecov.io/gh/hadilq/AndroidLifecycleHandler)

Android Lifecycle Handler
---
This library is a simplifier for unnecessary complex lifecycles of `androidx.lifecycle:lifecycle-extensions`. They're
complex in sense that most developers still have problem to handle lifecycle of their own creatures! for instance,
Android allows the lifecycle of their classes start from `onStart` callback and finish `onDestroy` callback! And just
adding the `onSaveInstanceState` callback to the collection of callbacks and boom! It's a hell then! Let's make a heaven
by simplifying it. In this library we support two types of lifecycle and both of them have `onBorn` and `onDie` callbacks,
not more.
First if you have no plane to save your data in the lifecycle, use an object of the `Life`. It's defined like bellow.

```kotlin
/***
 * Defines an object with simply two callbacks [onBorn] and [onDie] to complete the lifecycle. The time after
 * calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidLifecycleHandler] is able to register this object.
 */
interface Life {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the LifecycleOwner.
     */
    fun onBorn()

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop or onDestroy of the LifecycleOwner.
     */
    fun onDie()
}
```

Then if you want to save your data for the next lifecycle, use an object of the `ExtendedLife`.
```kotlin
/***
 * Defines an object with simply two callbacks [onBorn] and [onDie] to complete the lifecycle. The time after
 * calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidExtendedLifecycleHandler] is able to register this object.
 */
interface ExtendedLife {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the SavedStateRegistryOwner.
     */
    fun onBorn(bundle: Bundle)

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop, onDestroy or onSaveInstanceState of the SavedStateRegistryOwner.
     */
    fun onDie(): Bundle
}

```
They're self explanatory.

Also the `LifeSpan` is a measure of how long your creature wants to be alive.
```kotlin
/**
 * Defines how long the life would be alive.
 */
enum class LifeSpan {

    /**
     * In case of object being [Life], it's alive after onCreate and before onDestroy of LifecycleOwner.
     * In case of object being [ExtendedLife], it's alive after onCreate and before onDestroy or onSaveInstanceState
     * of SavedStateRegistryOwner, depends which one is called sooner.
     */
    CREATED,

    /**
     * In case of object being [Life], it's alive after onStart and before onStop or onDestroy of LifecycleOwner,
     * depends which one is called sooner.
     * In case of object being [ExtendedLife], it's alive after onStart and before onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner.
     */
    STARTED,

    /**
     * In case of object being [Life], it's alive after onResume and before onPause or onStop or onDestroy of
     * LifecycleOwner, depends which one is called sooner.
     * In case of object being [ExtendedLife], it's alive after onResume and before onPause or onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner
     */
    RESUMED
}
```

Nice! Now you have a zoo filled with different creatures!

Usage
---
This source has a sample app, which doesn't do anything, where you can find the usage in `MainActivity`.

```kotlin
class MainActivity : ComponentActivity() {
    
    private val creature = object: ExtendedLife{
        
        override fun onBorn(bundle: Bundle) {
            // Start being alive
        }

        override fun onDie(): Bundle = Bunlde () // Dead
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ...

        AndroidExtendedLifecycleHandler().register(this, creature, LifeSpan.CREATED, KEY)
    }
    
    companion object {
        private const val KEY = "KEY_TO_SAVE_THE_BUNDLE"
    }
}
```

Enjoy!

Download
---
Download via gradle
```groovy
implementation "com.github.hadilq:androidlifecyclehandler:$libVersion"
```
where the `libVersion` is [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/androidlifecyclehandlerbadge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/androidlifecyclehandler)

Contribution
---
Just create your branch from the master branch, change it, write additional tests, satisfy all 
tests, create your pull request, thank you, you're awesome.

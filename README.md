[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/android-lifecycle-handler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/android-lifecycle-handler)
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
 * [AndroidLifeHandler] is able to register this object.
 */
interface Life {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the LifecycleOwner.
     */
    @MainThread
    fun onBorn()

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop or onDestroy of the LifecycleOwner.
     */
    @MainThread
    fun onDie()
}
```

Then if you want to save your data for the next lifecycle, use an object of the `ELife`.
```kotlin
/***
 * Defines an object with **extended** lifecycle respect to [Life]. It has simply two callbacks [onBorn] and [onDie] to
 * complete the lifecycle. The time after calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidELifeHandler] is able to register this object.
 */
interface ELife {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the SavedStateRegistryOwner.
     *
     * The [bundle] would be null in case of first born. After first die, [bundle] must be from the previous dead
     * instance.
     */
    @MainThread
    fun onBorn(bundle: Bundle?)

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop, onDestroy or onSaveInstanceState of the SavedStateRegistryOwner.
     *
     * If you need to set the returning bundle to be null, rethink please, because it's probable that you need an
     * instance of [Life] instead of [ELife]!
     */
    @MainThread
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
     * This [LifeSpan] is just for [Life] objects who want to expand their life over multiple activities of a
     * user flow, which means onDestroy would not make them die. Check out [LifeStore.provideLife] function for
     * more details. Assuming the user flow includes multiple Activities.
     */
    USER_FLOW,

    /**
     * This [LifeSpan] is just for [Life] objects who want to expand their life over their owner activity, which
     * means configuration changes would not make them die. Check out [FragmentActivity.provideLife] extension
     * function for more details.
     */
    CONFIGURATION_CHANGED,

    /**
     * In case of object being [Life], it's alive after onCreate and before onDestroy of LifecycleOwner.
     * In case of object being [ELife], it's alive after onCreate and before onDestroy or onSaveInstanceState
     * of SavedStateRegistryOwner, depends which one is called sooner.
     */
    CREATED,

    /**
     * In case of object being [Life], it's alive after onStart and before onStop or onDestroy of LifecycleOwner,
     * depends which one is called sooner.
     * In case of object being [ELife], it's alive after onStart and before onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner.
     */
    STARTED,

    /**
     * In case of object being [Life], it's alive after onResume and before onPause or onStop or onDestroy of
     * LifecycleOwner, depends which one is called sooner.
     * In case of object being [ELife], it's alive after onResume and before onPause or onStop or onDestroy or
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

    private val creature = object : ELife {

        override fun onBorn(bundle: Bundle?) {
            // Start being alive
        }

        override fun onDie(): Bundle = Bundle() // Dead
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        AndroidELifeHandlerImpl().register(this, creature, LifeSpan.CREATED, KEY)
    }

    companion object {
        private const val KEY = "KEY_TO_SAVE_THE_BUNDLE"
    }
}
```

Enjoy!

Download
---
Since version 0.4.0 this library is a Kotlin Multiplatdform library, so depend on what kind of module you use it in, you can use different artifact IDs. Download via gradle
```groovy
implementation "com.github.hadilq:android-lifecycle-handler-android:$libVersion"
```
where the `libVersion` is [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/android-lifecycle-handler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hadilq/android-lifecycle-handler)
Use the above one if you already using this library or you need it in an android module. Use
```groovy
implementation "com.github.hadilq:android-lifecycle-handler-jvm:$libVersion"
```
if you need **only** the interfaces in a Java module. And finally, in a common module of multiplatform project you would use those interfaces as
```groovy
implementation "com.github.hadilq:android-lifecycle-handler-metadata:$libVersion"
```

Contribution
---
Just create your branch from the master branch, change it, write additional tests, satisfy all 
tests, create your pull request, thank you, you're awesome.

/**
 * Copyright 2020 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.hadilq.androidlifecyclehandler

import android.os.Bundle

/***
 * Defines an object with simply two callbacks [onBorn] and [onDie] to complete the lifecycle. The time after calling
 * of [onBorn] and before [onDie] is when the object is alive.
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

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
import androidx.annotation.MainThread

/***
 * Defines an object with **extended** lifecycle respect to [Life]. It has simply two callbacks [onBorn] and [onDie] to
 * complete the lifecycle. The time after calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidELifeHandler] is able to register this object.
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
interface ELife : DLife<Bundle> {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the SavedStateRegistryOwner.
     *
     * The [bundle] would be null in case of first born. After first die, [bundle] must be from the previous dead
     * instance.
     */
    @MainThread
    override fun onBorn(bundle: Bundle?)

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop, onDestroy or onSaveInstanceState of the SavedStateRegistryOwner.
     *
     * If you need to set the returning bundle to be null, rethink please, because it's probable that you need an
     * instance of [Life] instead of [ELife]!
     */
    @MainThread
    override fun onDie(): Bundle
}

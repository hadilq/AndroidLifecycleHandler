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

import androidx.savedstate.SavedStateRegistryOwner

/**
 * The handler to keep all parts together and handle calling of [born] and [die] of [ExtendedLife],
 * based on [LifeSpan] that they [register] by.
 */
interface AndroidExtendedLifecycleHandler {

    /**
     * Registers an instance of [life], based on the [lifeSpan], to handle calling of [ExtendedLife.onBorn] and
     * [ExtendedLife.onDie]. It'll unregister itself when onDestroy gets called.
     *
     * The [owner] is responsible to avoid GC to collect this object, by keeping a reference to it.
     *
     * The [key] is used to save and restore to SavedStateRegistry. So it must be unique between all handlers of one
     * [owner].
     */
    fun register(owner: SavedStateRegistryOwner, life: ExtendedLife, lifeSpan: LifeSpan, key: String)
}

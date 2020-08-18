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

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner

/**
 * The handler to keep all parts together and handle calling of [onBorn] and [onDie] of [Life],
 * based on [LifeSpan] that they [register] by.
 */
interface AndroidLifeHandler : LifeHandler {

    /**
     * Registers an instance of [life], based on the [lifeSpan], to handle calling of [Life.onBorn] and
     * [Life.onDie]. It'll unregister itself when onDestroy gets called.
     *
     * The [owner] is responsible to avoid GC to collect this object, by keeping a reference to it.
     */
    @MainThread
    fun register(owner: LifecycleOwner, life: Life, lifeSpan: LifeSpan)
}

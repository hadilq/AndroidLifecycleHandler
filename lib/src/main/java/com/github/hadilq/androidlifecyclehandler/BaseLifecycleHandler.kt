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

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BaseLifecycleHandler : LifecycleObserver {

    protected abstract val lifecycle: Lifecycle
    protected abstract val lifeSpan: LifeSpan

    protected abstract fun born()
    protected abstract fun die()
    protected abstract fun unregister()

    protected fun bornIfPossible() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && lifeSpan == LifeSpan.RESUMED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && lifeSpan == LifeSpan.STARTED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) && lifeSpan == LifeSpan.CREATED) {
            born()
        }
    }

    private fun dieIfPossible() {
        if (lifeSpan == LifeSpan.RESUMED) {
            die()
        } else if ((lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) ||
                lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) &&
            lifeSpan == LifeSpan.STARTED
        ) {
            die()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED) && lifeSpan == LifeSpan.CREATED) {
            die()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        dieIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        dieIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dieIfPossible()
        unregister()
    }
}

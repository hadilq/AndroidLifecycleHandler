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
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

class TestSaveStateRegistryOwner : SavedStateRegistryOwner {
    private val registry = LifecycleRegistry(this)
    private val controller = SavedStateRegistryController.create(this)

    val currentState: Lifecycle.State
        @NonNull
        get() = registry.currentState

    fun create(): TestSaveStateRegistryOwner {
        controller.performRestore(Bundle())
        return handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    fun start(): TestSaveStateRegistryOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun resume(): TestSaveStateRegistryOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun pause(): TestSaveStateRegistryOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    fun stop(): TestSaveStateRegistryOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun destroy(): TestSaveStateRegistryOwner {
        return handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    fun save(): TestSaveStateRegistryOwner {
        controller.performSave(Bundle())
        return this
    }

    private fun handleLifecycleEvent(@NonNull event: Lifecycle.Event): TestSaveStateRegistryOwner {
        registry.handleLifecycleEvent(event)
        return this
    }

    @NonNull
    override fun getLifecycle(): Lifecycle {
        return registry
    }

    override fun getSavedStateRegistry(): SavedStateRegistry = controller.savedStateRegistry
}

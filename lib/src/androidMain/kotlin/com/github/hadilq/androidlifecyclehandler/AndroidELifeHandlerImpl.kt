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
import androidx.lifecycle.Lifecycle
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner

/**
 * The implementation of [AndroidELifeHandler].
 */
class AndroidELifeHandlerImpl : BaseLifeHandler(), SavedStateRegistry.SavedStateProvider,
    AndroidELifeHandler {

    override val lifecycle by lazy { owner.lifecycle }
    private val savedStateRegistry by lazy { owner.savedStateRegistry }
    private var alive: Boolean = false
    private var keepBundle: Bundle? = null

    override lateinit var lifeSpan: LifeSpan
    private lateinit var owner: SavedStateRegistryOwner
    private lateinit var life: ELife
    private lateinit var key: String

    @MainThread
    override fun register(owner: SavedStateRegistryOwner, life: ELife, lifeSpan: LifeSpan, key: String) {
        this.owner = owner
        this.life = life
        this.lifeSpan = lifeSpan
        this.key = key
        registerIfPossible()
    }

    private fun registerIfPossible() {
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(this)
            savedStateRegistry.registerSavedStateProvider(key, this)
            bornIfPossible()
        }
    }

    @MainThread
    override fun born() {
        if (!alive) {
            alive = true
            life.onBorn(savedStateRegistry.consumeRestoredStateForKey(key) ?: keepBundle)
        }
    }

    @MainThread
    override fun die() {
        if (alive) {
            alive = false
            keepBundle = life.onDie()
        }
    }

    @MainThread
    override fun unregister() {
        lifecycle.removeObserver(this)
        savedStateRegistry.unregisterSavedStateProvider(key)
    }

    override fun saveState(): Bundle = if (alive) {
        alive = false
        life.onDie().apply { keepBundle = this }
    } else {
        keepBundle ?: Bundle()
    }
}

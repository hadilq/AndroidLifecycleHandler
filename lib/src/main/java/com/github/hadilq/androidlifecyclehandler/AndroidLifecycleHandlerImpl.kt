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
import androidx.lifecycle.LifecycleOwner

/**
 * The implementation of [AndroidLifecycleHandler].
 */
class AndroidLifecycleHandlerImpl : BaseLifecycleHandler(), AndroidLifecycleHandler {

    override val lifecycle by lazy { owner.lifecycle }
    private var alive: Boolean = false

    override lateinit var lifeSpan: LifeSpan
    private lateinit var owner: LifecycleOwner
    private lateinit var life: Life

    override fun register(owner: LifecycleOwner, life: Life, lifeSpan: LifeSpan) {
        this.owner = owner
        this.life = life
        this.lifeSpan = lifeSpan
        registerIfPossible()
    }

    private fun registerIfPossible() {
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(this)
            bornIfPossible()
        }
    }

    override fun born() {
        if (!alive) {
            alive = true
            life.onBorn()
        }
    }

    override fun die() {
        if (alive) {
            alive = false
            life.onDie()
        }
    }

    override fun unregister() {
        lifecycle.removeObserver(this)
    }
}

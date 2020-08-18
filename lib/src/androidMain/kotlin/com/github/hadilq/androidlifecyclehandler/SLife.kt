package com.github.hadilq.androidlifecyclehandler

import androidx.annotation.MainThread

/**
 * Defines a [Life] object that can **sync** other lives with the same lifecycle.
 */
abstract class SLife : Life {

    private var isAlive = false

    private val lives = mutableSetOf<Life>()

    protected fun Life.sync(): Boolean = lives.add(this).also { if (isAlive) onBorn() }

    @MainThread
    override fun onBorn() {
        if (!isAlive) {
            isAlive = true
            lives.forEach { it.onBorn() }
        }
    }

    @MainThread
    override fun onDie() {
        if (isAlive) {
            isAlive = false
            lives.forEach { it.onDie() }
            lives.clear()
        }
    }
}

package com.github.hadilq.androidlifecyclehandler

/**
 * Defines a [Life] object that can **sync** other lives with the same lifecycle.
 */
abstract class SLife : Life {

    private var isAlive = false

    private val lives = mutableSetOf<Life>()

    protected fun Life.sync(): Boolean = lives.add(this).also { if (isAlive) onBorn() }

    /**
     * It doesn't make sense to make [onBorn] and [onDie] methods thread-safe.
     */
    override fun onBorn() {
        if (!isAlive) {
            isAlive = true
            lives.forEach { it.onBorn() }
        }
    }

    /**
     * It doesn't make sense to make [onBorn] and [onDie] methods thread-safe.
     */
    override fun onDie() {
        if (isAlive) {
            isAlive = false
            lives.forEach { it.onDie() }
            lives.clear()
        }
    }
}

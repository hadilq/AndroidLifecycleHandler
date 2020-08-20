package com.github.hadilq.androidlifecyclehandler

/**
 * A factory to provide [Life] if needed.
 */
interface LifeFactory<out T : Life> {

    /**
     * Creates a life.
     */
    fun get(): T
}

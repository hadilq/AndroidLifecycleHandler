package com.github.hadilq.androidlifecyclehandler

/***
 * Defines an object with **extended** lifecycle respect to [Life]. It has simply two callbacks [onBorn] and [onDie] to
 * complete the lifecycle. The time after calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [LifeHandler] is able to register this object.
 */
interface DLife<DNA> {

    /**
     * The [dna] would be null in case of first born. After first die, [dna] must be from the previous dead
     * instance.
     */
    fun onBorn(dna: DNA?)

    /**
     * If you need to set the returning dna to be null, rethink please, because it's probable that you need an
     * instance of [Life] instead of [DLife]!
     */
    fun onDie(): DNA
}

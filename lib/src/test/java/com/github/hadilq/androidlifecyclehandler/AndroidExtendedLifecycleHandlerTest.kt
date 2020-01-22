package com.github.hadilq.androidlifecyclehandler

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AndroidExtendedLifecycleHandlerTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var life: ExtendedLife

    private lateinit var owner: TestSaveStateRegistryOwner
    private lateinit var handler: AndroidExtendedLifecycleHandlerImpl

    @Before
    fun setup() {
        owner = TestSaveStateRegistryOwner()
        handler = AndroidExtendedLifecycleHandlerImpl()
    }

    @Test
    fun `in case of just registration, CREATED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        verify(life, never()).onBorn(any())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, CREATED life should not be die`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()

        verify(life).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then save, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.save()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then save, CREATED life should never born and die`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.save()

        verify(life, never()).onBorn(any())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then save then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then save then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then save then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then save then pause then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.save()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then save then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then save then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of create then registration, STARTED life should not be born or die`() {
        owner.create()

        handler.register(owner, life, LifeSpan.STARTED, KEY)

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of just registration, STARTED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, STARTED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start, STARTED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()

        verify(life).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, STARTED life should not born or die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then save then destroy, STARTED life should not born or die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.save()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then destroy then create, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.destroy()
        owner.start()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then save then destroy then start, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.save()
        owner.destroy()
        owner.start()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then save then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then save then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start, STARTED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then start then resume, STARTED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.start()
        owner.resume()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start then stop, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.stop()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then then resume then pause stop then start then resume then pause then stop, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start then stop then destroy, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start then save, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.save()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then save then pause then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.save()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then save then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then save then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of create then start then registration, RESUMED life should not be born or die`() {
        owner.create()
        owner.start()

        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of just registration, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then start then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()

        verify(life).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.resume()

        verify(life).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then resume then destroy then create, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.resume()
        owner.destroy()
        owner.resume()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then resume then save then destroy then create, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.resume()
        owner.save()
        owner.destroy()
        owner.resume()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then save then stop then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then save then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life, never()).onBorn(anyOrNull())
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then resume, RESUMED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.resume()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then resume then pause, RESUMED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.resume()
        owner.pause()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then start then resume then pause then stop, RESUMED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()

        verify(life, times(2)).onBorn(anyOrNull())
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then save then pause then stop then destroy, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.save()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then save then stop then destroy, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.save()
        owner.stop()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then save then destroy, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.save()
        owner.destroy()

        verify(life).onBorn(anyOrNull())
        verify(life).onDie()
    }

    companion object {
        private const val KEY = "SAVE_KEY"
    }
}

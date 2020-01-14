package com.github.hadilq.androidlifecyclehandler

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class AndroidLifecycleHandlerTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var life: Life

    private lateinit var owner: TestLifecycleOwner
    private lateinit var handler: AndroidLifecycleHandler

    @Before
    fun setup() {
        owner = TestLifecycleOwner()
        handler = AndroidLifecycleHandler()
    }

    @Test
    fun `in case of destroy then registration then create, CREATED life should not be born or die`() {
        owner.destroy()

        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of just registration, CREATED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.CREATED)

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, CREATED life should not be die`() {
        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then destroy then create, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()
        owner.destroy()
        owner.create()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of just registration, STARTED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.STARTED)

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, STARTED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then start, STARTED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.start()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, STARTED life should not born or die`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.destroy()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start, STARTED life should born nut not die`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then destroy then create, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.destroy()
        owner.start()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()

        verify(life, times(2)).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start then destroy, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.destroy()

        verify(life, times(2)).onBorn()
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then start then stop then destroy, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life, times(2)).onBorn()
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of just registration, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start, RESUMED life should not be born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.start()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then start then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.start()
        owner.resume()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.resume()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.destroy()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then resume, RESUMED life should born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.resume()

        verify(life).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then resume then destroy then create, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.resume()
        owner.destroy()
        owner.resume()

        verify(life).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then resume then pause then resume, RESUMED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.resume()
        owner.pause()
        owner.resume()

        verify(life, times(2)).onBorn()
        verify(life).onDie()
    }

    @Test
    fun `in case of registration then create then resume then pause then resume then destroy, RESUMED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.resume()
        owner.pause()
        owner.resume()
        owner.destroy()

        verify(life, times(2)).onBorn()
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then resume then pause then resume then pause then destroy, RESUMED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.resume()
        owner.pause()
        owner.resume()
        owner.pause()
        owner.destroy()

        verify(life, times(2)).onBorn()
        verify(life, times(2)).onDie()
    }

    @Test
    fun `in case of registration then create then start then stop then destroy, RESUMED life should not born or die`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.start()
        owner.stop()
        owner.destroy()

        verify(life, never()).onBorn()
        verify(life, never()).onDie()
    }

    @Test
    fun `in case of registration then create then start then resume then pause then stop then destroy, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED)

        owner.create()
        owner.start()
        owner.resume()
        owner.pause()
        owner.stop()
        owner.destroy()

        verify(life).onBorn()
        verify(life).onDie()
    }
}

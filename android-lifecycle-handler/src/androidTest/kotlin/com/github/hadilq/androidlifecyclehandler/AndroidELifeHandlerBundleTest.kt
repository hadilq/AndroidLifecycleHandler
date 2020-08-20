package com.github.hadilq.androidlifecyclehandler

import android.os.Bundle
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.given
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
class AndroidELifeHandlerBundleTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var life: ELife

    @Mock
    private lateinit var bundle: Bundle

    private lateinit var owner: TestSaveStateRegistryOwner
    private lateinit var handler: AndroidELifeHandlerImpl

    @Before
    fun setup() {
        owner = TestSaveStateRegistryOwner()
        handler = AndroidELifeHandlerImpl()
    }

    @Test
    fun `in case of registration then create, CREATED life should not be die`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)
        val resultBundle = Bundle()
        given(bundle.getBundle(any())).willReturn(Bundle().apply { putBundle(KEY, resultBundle) })

        owner.create(bundle)

        val captor = argumentCaptor<Bundle>()
        verify(life).onBorn(captor.capture())
        verify(life, never()).onDie()
        assert(captor.firstValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then save, CREATED life should born and die once`() {
        handler.register(owner, life, LifeSpan.CREATED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.save(bundle)

        val captor = argumentCaptor<Bundle>()
        verify(bundle).putBundle(any(), captor.capture())
        assert(captor.firstValue.containsKey(KEY))
        assert(captor.firstValue.getBundle(KEY) === resultBundle)
    }

    @Test
    fun `in case of registration then create then start, STARTED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)
        val resultBundle = Bundle()
        given(bundle.getBundle(any())).willReturn(Bundle().apply { putBundle(KEY, resultBundle) })

        owner.create(bundle)
        owner.start()

        val captor = argumentCaptor<Bundle>()
        verify(life).onBorn(captor.capture())
        verify(life, never()).onDie()
        assert(captor.firstValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then start then destroy, STARTED life should born and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.start()
        owner.save(bundle)

        val captor = argumentCaptor<Bundle>()
        verify(bundle).putBundle(any(), captor.capture())
        assert(captor.firstValue.containsKey(KEY))
        assert(captor.firstValue.getBundle(KEY) === resultBundle)
    }

    @Test
    fun `in case of registration then create then start then start then stop then start, STARTED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()

        val captor = argumentCaptor<Bundle>()
        verify(life, times(2)).onBorn(captor.capture())
        verify(life).onDie()
        assert(captor.secondValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then start then stop then start then stop, STARTED life should born and die twice`() {
        handler.register(owner, life, LifeSpan.STARTED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.start()
        owner.stop()
        owner.start()
        owner.stop()

        val captor = argumentCaptor<Bundle>()
        verify(life, times(2)).onBorn(captor.capture())
        verify(life, times(2)).onDie()
        assert(captor.secondValue === resultBundle)
    }

    @Test
    fun `in case of registration then start then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)
        val resultBundle = Bundle()
        given(bundle.getBundle(any())).willReturn(Bundle().apply { putBundle(KEY, resultBundle) })

        owner.create(bundle)
        owner.start()
        owner.resume()

        val captor = argumentCaptor<Bundle>()
        verify(life).onBorn(captor.capture())
        verify(life, never()).onDie()
        assert(captor.firstValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then resume, RESUMED life should be born but not die`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)
        val resultBundle = Bundle()
        given(bundle.getBundle(any())).willReturn(Bundle().apply { putBundle(KEY, resultBundle) })

        owner.create(bundle)
        owner.resume()

        val captor = argumentCaptor<Bundle>()
        verify(life).onBorn(captor.capture())
        verify(life, never()).onDie()
        assert(captor.firstValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then resume then destroy then create, RESUMED life should born and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.resume()
        owner.save(bundle)

        val captor = argumentCaptor<Bundle>()
        verify(bundle).putBundle(any(), captor.capture())
        assert(captor.firstValue.containsKey(KEY))
        assert(captor.firstValue.getBundle(KEY) === resultBundle)
    }

    @Test
    fun `in case of registration then create then resume then pause then resume, RESUMED life should born twice and die once`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.resume()
        owner.pause()
        owner.resume()

        val captor = argumentCaptor<Bundle>()
        verify(life, times(2)).onBorn(captor.capture())
        verify(life).onDie()
        assert(captor.secondValue === resultBundle)
    }

    @Test
    fun `in case of registration then create then resume then pause then resume then pause, RESUMED life born and die twice`() {
        handler.register(owner, life, LifeSpan.RESUMED, KEY)
        val resultBundle = Bundle()
        given(life.onDie()).willReturn(resultBundle)

        owner.create()
        owner.resume()
        owner.pause()
        owner.resume()
        owner.pause()

        val captor = argumentCaptor<Bundle>()
        verify(life, times(2)).onBorn(captor.capture())
        verify(life, times(2)).onDie()
        assert(captor.secondValue === resultBundle)
    }

    companion object {
        private const val KEY = "SAVE_KEY"
        private const val VALUE = "VALUE"
    }
}

package com.binlly.gankee.business.demo.fragment

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertNotNull

/**
 * Created by yy on 2017/9/20.
 */
class DemoFragmentPresenterTest {

    private lateinit var argId: String

    @Before
    fun setUp() {
        argId = mock(String::class.java)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun handleArgument() {
        assertNotNull(argId)
    }

    @Test
    fun requestDemo() {

    }
}
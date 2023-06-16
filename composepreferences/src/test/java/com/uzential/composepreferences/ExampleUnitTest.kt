package com.uzential.composepreferences

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun t() {
        doSomethingWrapped("Test")
    }
}

fun doSomethingWrapped(a: String, b: Boolean = false, c: Int = 1) {
    runBlocking {// wrapper
        doSomething(a = a, b = b, c = c) // call original function
    }
}

fun doSomething(a: String, b: Boolean = false, c: Int = 1) {
    print("$a: $b | $c")
}

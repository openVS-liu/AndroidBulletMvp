package com.example.net

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
        var text=Test1()
        text.list.printT("jsjsjj-----")

        assertEquals(4, 2 + 2)
    }
    class Test1{
        var list=object : Listener<String> {
            override fun printT(t: String) {
                println(t)
            }

        }
    }
    interface  Listener<T:Any?>{

        fun printT(t:T)
    }
}

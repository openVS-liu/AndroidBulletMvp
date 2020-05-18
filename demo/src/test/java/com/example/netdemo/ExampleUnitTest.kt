package com.example.netdemo

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.pow

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(PowCalculation(10.0).pow(0))
        println(PowCalculation(10.0).pow(3))
        println(PowCalculation(10.0).pow(-3))
    }

    class PowCalculation(private val baseNumber: Double){
        fun pow( exponent: Int): Double {
            var m = baseNumber
            var n = exponent
            var ret = 1.0

            while (n != 0) {
                if (n % 2 != 0) {
                    ret *= m
                }
                n /= 2
                m *= m

            }
            return if (exponent>=0)  ret else 1/ret
        }
    }



}



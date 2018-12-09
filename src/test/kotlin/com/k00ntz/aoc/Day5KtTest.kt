package com.k00ntz.aoc

import org.junit.Test

import org.junit.Assert.*

class Day5KtTest {

    @Test
    fun day5() {
        val inStr = "dabAcCaCBAcCcaDA".toList()
        val remainingCount = com.k00ntz.aoc.day5(inStr)
        assertEquals(10, remainingCount)
    }

    @Test
    fun day5part2() {
        val inStr = "dabAcCaCBAcCcaDA".toList()
        val minCount = com.k00ntz.aoc.day5part2(inStr)
        assertEquals(4, minCount)
    }

    @Test
    fun matches() {
        assertTrue('a' matches 'A')
        assertFalse('A' matches 'A')
        assertFalse('b' matches 'A')
    }
}
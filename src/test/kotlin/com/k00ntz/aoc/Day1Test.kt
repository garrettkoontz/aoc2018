package com.k00ntz.aoc

import org.junit.Assert.assertEquals
import org.junit.Test

class Day1Test {

    @Test
    fun day1() {
        assertEquals(3L, day1(listOf(1L,1L,1L)))
        assertEquals(0L, day1(listOf(1L,1L,-2L)))
        assertEquals(-6L, day1(listOf(-1L,-2L,-3L)))
    }

    @Test
    fun day1part2() {
        assertEquals(0L, day1part2(listOf(1L, -1L)))
        assertEquals(10L, day1part2(listOf(3L, 3L, 4L, -2L, -4L)))
        assertEquals(5L, day1part2(listOf(-6L, 3L, 8L, 5L, -6L)))
        assertEquals(14L, day1part2(listOf(7L, 7L, -2L, -7L, -4L)))
    }
}
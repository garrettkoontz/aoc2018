package com.k00ntz.aoc

import org.junit.Test

import org.junit.Assert.*

class Day2KtTest {

    @Test
    fun day2() {
        val lst = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
        )
        assertEquals(12, day2(lst))
    }

    @Test
    fun getCountMap() {
        assertEquals(mapOf(Pair('b', 3), Pair('a', 2)), getCountMap("babab".toList()))
    }

    @Test
    fun day2part2(){
        val lst = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        )
        assertEquals("fgij", com.k00ntz.aoc.day2part2(lst))
    }
}
package com.k00ntz.aoc

import org.junit.Assert.assertEquals
import org.junit.Test

class Day7KtTest {

    @Test
    fun day7() {
        val strs = listOf(
            CharLine('C', 'A'),
            CharLine('C', 'F'),
            CharLine('A', 'B'),
            CharLine('A', 'D'),
            CharLine('B', 'E'),
            CharLine('D', 'E'),
            CharLine('F', 'E')
        )
        assertEquals("CABDFE", day7(strs))

    }

    @Test
    fun getTaskLength() {
        val getTsLn = getTaskLength(0)
        assertEquals(1, getTsLn('A'))
        assertEquals(26, getTsLn('Z'))
    }

    @Test
    fun day7part2() {
        val strs = listOf(
            CharLine('C', 'A'),
            CharLine('C', 'F'),
            CharLine('A', 'B'),
            CharLine('A', 'D'),
            CharLine('B', 'E'),
            CharLine('D', 'E'),
            CharLine('F', 'E')
        )
        assertEquals(15, com.k00ntz.aoc.day7part2(strs, 2, 0))

    }
}
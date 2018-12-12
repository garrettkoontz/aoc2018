package com.k00ntz.aoc

import org.junit.Assert.assertEquals
import org.junit.Test

class Day8KtTest {

    @Test
    fun checkSum() {
        val node1 = Node(listOf(1, 2), emptyList())
        val node2 = Node(listOf(3, 4), emptyList())
        val node3 = Node(listOf(5), listOf(node1, node2))
        assertEquals(15, checkSum(node3))
    }

    @Test
    fun day8() {
        val ints = listOf(2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2)
        assertEquals(138, day8(ints))
    }

    @Test
    fun day8part2() {
        val ints = listOf(2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2)
        assertEquals(66, day8part2(ints))
    }
}
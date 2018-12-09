package com.k00ntz.aoc.utils

import org.junit.Test

import org.junit.Assert.*

class HelperFnKtTest {

    @Test
    fun convexHull() {
        val hull = com.k00ntz.aoc.utils.convexHull(
            listOf(
                Point(1, 1),
                Point(1, 6),
                Point(8, 3),
                Point(3, 4),
                Point(5, 5),
                Point(8, 9)
            )
        )
        assertEquals(setOf(Point(1, 1), Point(1, 6), Point(8, 3), Point(8, 9)), hull.toSet())
    }
}
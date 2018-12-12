package com.k00ntz.aoc

import org.junit.Assert.assertEquals
import org.junit.Test

class Day9KtTest {

    @Test
    fun day9Test() {
        val scores = Board(9, 25).play()
        assertEquals(32L, scores.max())
    }

    @Test
    fun day9Debug() {
        val board10 = Board(10, 1618).play()

    }

    @Test
    fun day9() {
        assertEquals(8317L, Board(10, 1618).play().max())
        assertEquals(146373L, Board(13, 7999).play().max())
        assertEquals(2764L, Board(17, 1104).play().max())
        assertEquals(54718L, Board(21, 6111).play().max())
        assertEquals(37305L, Board(30, 5807).play().max())
    }


}
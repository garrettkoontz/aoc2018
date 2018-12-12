package com.k00ntz.aoc

import org.junit.Test

import org.junit.Assert.*

class Day9KtTest {

    @Test
    fun day9Test() {
        val scores = Board(9, 25).play()
        assertEquals(32, scores.max())
    }

    @Test
    fun day9Debug() {
        val board10 = Board(10, 1618).play()

    }

    @Test
    fun day9() {
        assertEquals(8317, Board(10, 1618).play().max())
        assertEquals(146373, Board(13, 7999).play().max())
        assertEquals(2764, Board(17, 1104).play().max())
        assertEquals(54718, Board(21, 6111).play().max())
        assertEquals(37305, Board(30, 5807).play().max())
    }


}
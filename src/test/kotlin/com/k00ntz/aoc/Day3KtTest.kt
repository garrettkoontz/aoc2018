package com.k00ntz.aoc

import org.junit.Test

import org.junit.Assert.*

class Day3KtTest {

    @Test
    fun day3() {
        val claimStrings = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        assertEquals(4, day3(claimStrings.map{ day3ParseFn(it)}, 10))
    }

    @Test
    fun day3part2() {
        val claimStrings = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        assertEquals(3, day3part2(claimStrings.map{ day3ParseFn(it)}, 10))
    }

    @Test
    fun applyClaim() {
        val cloth: Array<Array<MutableSet<Int>>> =
            (0..4).map { (0..4).map { mutableSetOf<Int>() }.toTypedArray() }.toTypedArray()
        com.k00ntz.aoc.applyClaim(10, listOf(Pair(2, 2), Pair(3, 2), Pair(2, 3), Pair(3, 3)), cloth)
//        println(cloth.map { it.toList() }.toList())
        assertEquals(setOf(10), cloth[2][2])
        assertEquals(setOf(10), cloth[2][3])
        assertEquals(setOf(10), cloth[3][2])
        assertEquals(setOf(10), cloth[3][3])
    }

    @Test
    fun getPoints1() {
        val claim = Claim(123, 1, 1, 1, 1)
        val pts = com.k00ntz.aoc.getPoints(claim)
        assertEquals(listOf(Pair(2, 2)), pts)
    }

    @Test
    fun getPoints2() {
        val claim = Claim(123, 1, 1, 2, 2)
        val pts = com.k00ntz.aoc.getPoints(claim)
        assertEquals(setOf(Pair(2, 2), Pair(3, 2), Pair(2, 3), Pair(3, 3)), pts.toSet())
    }
}
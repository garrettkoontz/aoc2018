package com.k00ntz.aoc

import com.k00ntz.aoc.utils.Point
import org.junit.Test

import org.junit.Assert.*

class Day11KtTest {

    @Test
    fun powerLevel() {
        assertEquals(4, powerLevel(8)(Point(3, 5)))
        assertEquals(-5, powerLevel(57)(Point(122, 79)))
        assertEquals(0, powerLevel(39)(Point(217, 196)))
        assertEquals(4, powerLevel(71)(Point(101, 153)))
    }

    @Test
    fun getPowerTest() {
        "".let {
            val powerGrid = powerGrid(300, com.k00ntz.aoc.powerLevel(18))
            val getPower = getPower(powerGrid, hashMapOf())
            (1..2).map { getPower(it)(33, 45) }
            assertEquals(29, getPower(3)(33, 45))
        }
        "".let {
            val powerGrid = powerGrid(300, com.k00ntz.aoc.powerLevel(42))
            val getPower = getPower(powerGrid, hashMapOf())
            (1..2).map { getPower(it)(21,61) }
            assertEquals(30, getPower(3)(21,61))
        }
    }

    @Test
    fun findTotalPower() {
        assertEquals(
            Pair(Point(33, 45), 29),
            com.k00ntz.aoc.findTotalPower(powerGrid(300, com.k00ntz.aoc.powerLevel(18)))
        )
        assertEquals(
            Pair(Point(21, 61), 30),
            com.k00ntz.aoc.findTotalPower(powerGrid(300, com.k00ntz.aoc.powerLevel(42)))
        )
    }

    @Test
    fun day11part2() {
        assertEquals(
            Pair(16, Pair(Point(90, 269), 113)),
            findMaxSummedArea(summedAreaTable(com.k00ntz.aoc.powerLevel(18), 301))
        )
        assertEquals(
            Pair(12, Pair(Point(232,251), 119)),
            findMaxSummedArea(summedAreaTable(com.k00ntz.aoc.powerLevel(42), 301))
        )
    }
}
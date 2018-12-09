package com.k00ntz.aoc

import com.k00ntz.aoc.utils.Point
import org.junit.Test

import org.junit.Assert.*

class Day6KtTest {

    @Test
    fun day6Test() {
        val points = listOf(
            Point(1, 1),
            Point(1, 6),
            Point(8, 3),
            Point(3, 4),
            Point(5, 5),
            Point(8, 9)
        )
        assertEquals(17, day6(points))
    }

    @Test
    fun day6part2Test(){
        val points = listOf(
            Point(1, 1),
            Point(1, 6),
            Point(8, 3),
            Point(3, 4),
            Point(5, 5),
            Point(8, 9)
        )
        assertEquals(16, day6part2(points, 32))
    }

    @Test
    fun buildFromPoints(){
        val points = listOf(
            Point(1, 1),
            Point(1, 6),
            Point(8, 3),
            Point(3, 4),
            Point(5, 5),
            Point(8, 9)
        )
        assertEquals(17, buildFromPoints(points))
    }

    @Test
    fun findNeighbors() {
        val inPoint1 = Point(0, 0)
        val inPoint2 = Point(-3, -4)
        val pts = listOf(Point(1, 1), Point(-2, -2))
        val neighbor1 = com.k00ntz.aoc.findNeighborIndex(inPoint1, pts)
        assertEquals(0, neighbor1)
        val neighbor2 = com.k00ntz.aoc.findNeighborIndex(inPoint2, pts)
        assertEquals(1, neighbor2)
        val neighbor3 = com.k00ntz.aoc.findNeighborIndex(Point(0, 0), listOf(Point(1, 1), Point(-1, -1)))
        assertNull(neighbor3)
    }

    @Test
    fun manhattanPoints() {
        assertEquals(
            setOf(Point(-1, 0), Point(0, -1), Point(1, 0), Point(0, 1)),
            com.k00ntz.aoc.manhattanPoints(Point(0, 0)).toSet()
        )
    }

    @Test
    fun removeConflicts() {
        val point1 = Point(1, 1)
        val point2 = Point(2, 2)
        val map = mapOf(
            Pair(0, com.k00ntz.aoc.manhattanPoints(point1).toSet()),
            Pair(1, com.k00ntz.aoc.manhattanPoints(point2).toSet())
        )
        val mp = removeConflicts(
            map,
            emptyList(),
            mutableSetOf()
        )
        assertFalse(mp.first.values.flatten().contains(Point(1, 2)))
        assertFalse(mp.first.values.flatten().contains(Point(2, 1)))
        assertEquals(setOf(Point(1,2), Point(2,1)), mp.second)
    }
}
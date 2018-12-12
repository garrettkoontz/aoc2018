package com.k00ntz.aoc.utils

import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt


data class Particle(
    val position: Point = Pair(0, 0),
    val velocity: Point = Pair(0, 0),
    val acceleration: Point = Pair(0, 0)
) {
    fun move(): Particle {
        return Particle(position + velocity, velocity + acceleration, acceleration)
    }

    fun distanceTo(particle: Particle): Double {
        val xDiff = (this.position.x() - particle.position.x()).toDouble()
        val yDiff = (this.position.y() - particle.position.y()).toDouble()
        return sqrt(xDiff * xDiff + yDiff * yDiff)
    }

}


class Grid(
    val origin: Point,
    // grid is a x by y array of arrays of Char
    val grid: Array<Array<Particle?>>,
    val pointChar: Char,
    val notPointChar: Char
) {
    private val maxY: Int = grid.first().size
    private val maxX: Int = grid.size

    private fun isScaledPointOutOfBoundary(pt: Point): Boolean {
        return abs(pt.x()) > maxX || abs(pt.y()) > maxY
    }

    private fun scaleParticle(pt: Particle): Point = origin + pt.position

    fun addParticle(pt: Particle) {
        val inGridPoint = scaleParticle(pt)
        if (isScaledPointOutOfBoundary(inGridPoint)) {
            throw RuntimeException("Unable to add out of bounds point: $pt")
        } else {
            grid[inGridPoint.x()][inGridPoint.y()] = pt
        }
    }

    fun removeParticle(pt: Particle) {
        val inGridPoint = scaleParticle(pt)
        if (isScaledPointOutOfBoundary(inGridPoint)) {
            throw RuntimeException("Unable to add out of bounds point: $pt")
        } else {
            grid[inGridPoint.x()][inGridPoint.y()] = null
        }
    }

    fun transform(): Grid {
        grid.pmap {}
    }

    private fun transpose(): List<List<Char>> {
        val ret = ArrayList<List<Char>>()
        val N = grid[0].size
        for (i in 0 until N) {
            val col = ArrayList<Char>()
            for (row in grid) {
                col.add(if (row[i] != null) pointChar else notPointChar)
            }
            ret.add(col)
        }
        return ret
    }

    override fun toString(): String {
        return this.transpose().map { it.map { it.toString() }.joinToString(separator = " ") }
            .joinToString(separator = "\n")
    }
}

private fun <T> Array<T>.pmap(function: () -> Unit) {

}


fun List<Particle>.toGrid(
    pointChar: Char = '#',
    notPointChar: Char = '.'
): Grid {
    val maxX = this.maxBy { abs(it.position.x()) }!!.position.x()
    val maxY = this.maxBy { abs(it.position.y()) }!!.position.y()
    val origin = Point(maxX, maxY)
    val grid = Grid(
        origin,
        (-maxX..maxX).map { (-maxY..maxY).map { notPointChar }.toTypedArray() }.toTypedArray(),
        pointChar,
        notPointChar
    )
    this.forEach { grid.addPoint(it.position) }
    return grid
}
package com.k00ntz.aoc

import com.k00ntz.aoc.utils.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val day6FileName = "6-2018.txt"

val day6ParseFn = { s: String ->
    s.split(",", " ").let { Point(it.first().toInt(), it.last().toInt()) }
}

fun main(args: Array<String>) {
    println(day6(parseFile(day6FileName, day6ParseFn)))
    println(day6part2(parseFile(day6FileName, day6ParseFn), 10000))
}

fun day6part2(pts: List<Point>, maxDistance: Int): Int {
    val maxX: Int = pts.maxBy { it.first }!!.first
    val minX: Int = pts.minBy { it.first }!!.first
    val maxY: Int = pts.maxBy { it.second }!!.second
    val minY: Int = pts.minBy { it.second }!!.second
    val range = (min(minX, minY)..max(maxX, maxY))
    val gridPts = cartesian(range.toList())
    val ptsMap = gridPts.pmap { Pair(it, totalDistance(it, pts)) }.toMap()
    return ptsMap.filterValues { it < maxDistance }.size
}

fun totalDistance(pt: Point, pts: List<Point>): Int =
    pts.map { distanceTo(pt, it) }.sum()


fun distanceTo(pt: Point, otherPt: Point): Int =
    abs(pt.x() - otherPt.x()) + abs(pt.y() - otherPt.y())


@Deprecated("did another way, this was too slow")
fun day6Old(points: List<Point>): Int{
        val infinites = convexHull(points)
    val maxX: Int = points.maxBy { it.first }!!.first
    val minX: Int = points.minBy { it.first }!!.first
    val maxY: Int = points.maxBy { it.second }!!.second
    val minY: Int = points.minBy { it.second }!!.second
    val grid: Map<Point, Int?> = cartesian((minX..maxX).toList(), (minY..maxY).toList()).pmap { pt: Point ->
        Pair(pt, nearestPointIndex(pt, points))
    }.toMap()
    val counts = grid.map { Pair(it.key, it.value) }.groupBy { it.second }
    val removes = infinites.map { points.indexOf(it) }
    val valids = counts.filter { !removes.contains(it.key) }.mapValues { it.value.size }
    return valids.maxBy { it.value }!!.value
}

fun day6(points: List<Point>): Int {
    return buildFromPoints(points)
}

fun buildFromPoints(
    pts: List<Point>,
    maxX: Int = pts.maxBy { it.first }!!.first,
    minX: Int = pts.minBy { it.first }!!.first,
    maxY: Int = pts.maxBy { it.second }!!.second,
    minY: Int = pts.minBy { it.second }!!.second
): Int {
    val lst: List<MutableList<Set<Point>>> = pts.map { mutableListOf(setOf(it)) }
    var duds: MutableSet<Point> = mutableSetOf()
    val manFn = manhattanPointsWithBounds(minX, minY, maxX, maxY)
    for (i in (min(minX, minY)..max(maxX, maxY))) {
        val usedVals = lst.flatMap { it.flatten().toSet() }
        val (newVals, newDuds) = removeConflicts(lst.mapIndexed { index, mutableList ->
            Pair(
                index,
                mutableList.last().flatMap(manFn).toSet()
            )
        }.toMap(), usedVals, duds)
        duds.addAll(newDuds)
        lst.forEachIndexed { index, mutableList -> mutableList.add(newVals[index]!!) }
    }
    val flatList = lst.map { it.flatten().toSet() }
    return flatList.maxBy { it.size }!!.size
}

fun removeConflicts(
    mp: Map<Int, Set<Point>>,
    usedVals: List<Point>,
    duds: MutableSet<Point>
): Pair<Map<Int, Set<Point>>, Set<Point>> {
    val dupes = mp.values.flatten().groupBy { it }.filter { it.value.size > 1 }
    return Pair(mp.mapValues { (_, v) ->
        v.filter {
            !dupes.contains(it)
                    && !usedVals.contains(it)
                    && !duds.contains(it)
        }.toSet()
    }
        , dupes.keys)
}

@Deprecated("did another way")
fun nearestPointIndex(pt: Point, pts: List<Point>): Int? {
    return if (pts.contains(pt)) pts.indexOf(pt)
    else findNeighborIndex(pt, pts)
}

@Deprecated("did another way")
fun findNeighborIndex(pt: Point, pts: List<Point>): Int? {
    var nextPoints = manhattanPoints(pt)
    while (true) {
        val isNeighbor = pts.filter { nextPoints.contains(it) }
        if (isNeighbor.size == 1) {
            return pts.indexOf(isNeighbor.first())
        }
        if (isNeighbor.size > 1) return null
        nextPoints = nextPoints.flatMap { manhattanPoints(it) }
    }

}

fun manhattanPointsWithBounds(minX: Int, minY: Int, maxX: Int, maxY: Int) = { pt: Point ->
    manhattanPoints(pt).filter { (it.first in minX..maxX) && (it.second in minY..maxY) }
}

fun manhattanPoints(pt: Point): List<Point> =
    listOf(
        Point(pt.first + 1, pt.second),
        Point(pt.first - 1, pt.second),
        Point(pt.first, pt.second + 1),
        Point(pt.first, pt.second - 1)
    )
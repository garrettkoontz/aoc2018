package com.k00ntz.aoc

import com.k00ntz.aoc.utils.*

val day11FileName = "11-2018.txt"

val day11ParseFn = { s: String ->
    s.toInt()
}

fun main() {
    println(day11(parseLine(day11FileName, day11ParseFn)))
    println(day11part2(parseLine(day11FileName, day11ParseFn)))
}

fun day11part2(seed: Int, gridSize: Int = 301): Pair<Point, Int> {
    val powerFn = powerLevel(seed)
    val summedArea = summedAreaTable(powerFn, gridSize)
    val max = findMaxSummedArea(summedArea)
    return Pair(max.second.first, max.first)
}

fun summedAreaTable(powerFn: (Point) -> Int, gridSize: Int): Array<Array<Int>> {
    val summedArea = (0..gridSize + 1).map {
        (0..gridSize + 1).map { 0 }.toTypedArray()
    }.toTypedArray()
    (1 until gridSize + 1).forEach { x ->
        (1 until gridSize + 1).forEach { y ->
            summedArea[x][y] = powerFn(Point(x, y)) + summedArea[x - 1][y] + summedArea[x][y - 1] -
                    summedArea[x - 1][y - 1]
        }
    }
    return summedArea
}

fun findMaxSummedArea(summedArea: Array<Array<Int>>): Pair<Int, Pair<Point, Int>> {
    var best = 0
    var bestPair = Point(-1, -1)
    var bestSize = 0
    for (s in (1..300)) {
        for (x in (s..300)) {
            for (y in (s..300)) {
                val total = summedArea[x][y] + summedArea[x - s][y - s] - summedArea[x - s][y] - summedArea[x][y - s]
                if (total > best) {
                    best = total
                    bestPair = Point(x - s + 1, y - s + 1)
                    bestSize = s
                }
            }
        }
    }
    return Pair(bestSize, Pair(bestPair, best))
}

fun maxPowersAndSubGrid(
    powerGrid: Array<Array<Int>>,
    gridSize: Int = 300,
    memo: HashMap<Triple<Int, Int, Int>, Int> = hashMapOf()
): Pair<Int, Pair<Point, Int>> {
    val totalPowers = (1..gridSize).map { Pair(it, findTotalPower(powerGrid, it, memo)) }
    return totalPowers.maxBy { it.second.second }!!
}

fun day11(seed: Int, gridSize: Int = 300): Point {
    val powerFn = powerLevel(seed)
    val powerGrid = powerGrid(gridSize, powerFn)
    return findTotalPower(powerGrid).first
}

fun findTotalPower(
    powerGrid: Array<Array<Int>>,
    subGridSize: Int = 3,
    memo: HashMap<Triple<Int, Int, Int>, Int> = hashMapOf()
): Pair<Point, Int> {
    val getPower = getPower(powerGrid, memo)
    val pairs: List<Pair<Point, Int>> = (1..subGridSize).flatMap { sgs ->
        powerGrid.mapIndexed { xIndex, ys ->
            ys.mapIndexed { yIndex, _ ->
                if (xIndex + subGridSize >= powerGrid.size || yIndex + subGridSize >= powerGrid.size) {
                    Pair(Point(-1, -1), Int.MIN_VALUE)
                } else {
                    val pt = Point(xIndex, yIndex)
                    Pair(pt, getPower(sgs)(xIndex, yIndex))
                }
            }
        }
    }.flatten()
    return pairs.maxBy { it.second }!!
}

//prime grids combine

fun getPower(powerGrid: Array<Array<Int>>, memo: HashMap<Triple<Int, Int, Int>, Int>) =
    { subGridSize: Int ->
        { xIndex: Int, yIndex: Int
            ->
            if (subGridSize <= 1) {
                val sum = powerGrid[xIndex][yIndex]
                memo[Triple(subGridSize, xIndex, yIndex)] = sum
                sum
            } else {
                val subKey = Triple(subGridSize - 1, xIndex, yIndex)
                val subSum = memo[subKey]!! + powerGrid[xIndex + subGridSize - 1][yIndex + subGridSize - 1]
                val restSum = (0 until subGridSize - 1).flatMap {
                    val s1 = powerGrid[xIndex + it][yIndex + subGridSize - 1]
                    val s2 = powerGrid[xIndex + subGridSize - 1][yIndex + it]
                    listOf(s1, s2)
                }.sum()
                val sum = subSum + restSum
                memo[Triple(subGridSize, xIndex, yIndex)] = sum
                sum
            }

        }
    }

fun powerGrid(gridSize: Int, powerFn: (Point) -> Int): Array<Array<Int>> =
    (0 until gridSize).map { x ->
        (0 until gridSize).map { y -> if (x == 0 || y == 0) 0 else powerFn(Point(x, y)) }.toTypedArray()
    }.toTypedArray()


fun powerLevel(seed: Int) = { coord: Point ->
    val rackId = coord.x() + 10
    val initPower = rackId * coord.y()
    val increaseSeed = initPower + seed
    val timesRack = increaseSeed * rackId
    val keepHundreths = (timesRack / 100) % 10
    keepHundreths - 5
}

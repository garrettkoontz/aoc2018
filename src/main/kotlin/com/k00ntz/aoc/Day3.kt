package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseFile

val day3FileName = "3-2018.txt"

data class Claim(val id: Int, val leftEdge: Int, val topEdge: Int, val width: Int, val height: Int)

val regex = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".toRegex()
val day3ParseFn = { s: String ->
    regex.matchEntire(s)?.destructured?.let { (id, leftEdge, topEdge, width, height) ->
        Claim(id.toInt(), leftEdge.toInt(), topEdge.toInt(), width.toInt(), height.toInt())
    }!!
}

fun main(args: Array<String>) {
    println(day3(parseFile(day3FileName, day3ParseFn)))
    println(day3part2(parseFile(day3FileName, day3ParseFn)))
}

fun day3part2(claims: List<Claim>, clothSize: Int = 1000): Int{
    val cloth = createCloth(claims, clothSize)
    val pairs = cloth.flatMap { it.map { Pair(it, it.size) } }
    val dupes = pairs.filter { it.second > 1 }.flatMap { it.first }.toSet()
    return claims.map { it.id }.minus(dupes).first()
}

fun createCloth(claims: List<Claim>, clothSize: Int): Array<Array<MutableSet<Int>>> {
    val cloth: Array<Array<MutableSet<Int>>> =
        (0..clothSize).map { (0..clothSize).map { mutableSetOf<Int>() }.toTypedArray() }.toTypedArray()
    claims.forEach { applyClaim(it.id, getPoints(it), cloth) }
    return cloth
}

fun day3(claims: List<Claim>, clothSize: Int = 1000): Int {
    val cloth = createCloth(claims, clothSize)
    return cloth.flatMap { it.map { it.size } }.filter { it > 1 }.size
}

fun applyClaim(id: Int, pts: List<Pair<Int, Int>>, cloth: Array<Array<MutableSet<Int>>>) {
    pts.forEach { cloth[it.first][it.second].add(id) }
}

fun getPoints(claim: Claim): List<Pair<Int, Int>> =
    (1..claim.width).flatMap { i -> (1..claim.height).map { j -> Pair(claim.leftEdge + i, claim.topEdge + j) } }

// #123 @ 3,2: 5x4

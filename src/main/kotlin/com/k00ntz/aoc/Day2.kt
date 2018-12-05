package com.k00ntz.aoc

import com.k00ntz.aoc.utils.cartesian
import com.k00ntz.aoc.utils.parseFile

val fileName = "2-2018.txt"

fun main(args: Array<String>) {
    val parsefn = { i: String -> i }
    println("Day1 part 1: ${day2(parseFile(fileName = fileName, parsefn = parsefn))}")
    println("Day1 part 2: ${day2part2(parseFile(fileName = fileName, parsefn = parsefn))}")
}

fun day2part2(parseFile: List<String>): String {
    val pairs = cartesian(parseFile)
    val comparisons = pairs.associate { Pair(it, numberOfDifferentChars(it.first, it.second)) }
    val closest = comparisons.entries.first { it.value == (it.key.first.length - 1) }.key
    return diffStrings(closest.first, closest.second)
}

fun diffStrings(s1: String, s2: String): String =
    s1.foldIndexed("") { index, acc, c -> if (s2[index] == c) acc + c else acc }


fun numberOfDifferentChars(s1: String, s2: String): Int =
    s1.foldIndexed(0) { index, acc, c -> if (s2[index] == c) acc + 1 else acc }

fun day2(parseFile: List<String>): Int =
    checksum(parseFile.map { toCharCountMap(it) })


fun checksum(mp: List<Map<Char, Int>>): Int =
    getCountMap(mp.flatMap { it.values.toSet().filter { it in 2..3 } }).values.fold(1) { acc, i -> acc * i }


fun toCharCountMap(s: String): Map<Char, Int> =
    getCountMap(s.toCharArray().toList())


fun <T : Any> getCountMap(inp: List<T>): Map<T, Int> = inp.fold(mutableMapOf()) { acc, c ->
    if (acc.containsKey(c)) {
        val cnt = acc[c]!! + 1
        acc[c] = cnt
    } else {
        acc[c] = 1
    }
    acc
}

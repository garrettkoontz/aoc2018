package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseFile

val day5FileName = "5-2018.txt"

val day5ParseFn = { s: String ->
    s.toCharArray()
}

fun main(args: Array<String>) {
    println(day5(parseFile(day5FileName, day5ParseFn).first().toList()))
    println(day5part2(parseFile(day5FileName, day5ParseFn).first().toList()))
}

fun day5part2(chars: List<Char>): Int {
    val allChars = chars.map { it.toUpperCase() }.toSet()
    val reductions = allChars.parallelStream().map { fChar ->
        Pair(fChar, reduceChar(chars.filter { !it.equals(fChar, true) }))
    }!!
    return reductions.min { o1: Pair<Char, List<Char>>, o2: Pair<Char, List<Char>> ->
        compareValues(o1.second.size, o2.second.size)
    }.get().second.size
}

fun day5(chars: List<Char>): Int {
    return reduceChar(chars).size
}

fun reduceChar(chars: List<Char>): List<Char> {
    var i = 0
    var chrs = chars
    while (i != chrs.size - 1) {
        if (chrs[i] matches chrs[i + 1]) {
            chrs = chrs.subList(0, i) + chrs.subList(i + 2, chrs.size)
            if (i != 0) i--
        } else {
            i++
        }
    }
    return chrs
}

infix fun Char.matches(c1: Char): Boolean = this.toUpperCase() == c1.toUpperCase() && this != c1


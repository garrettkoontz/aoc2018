package com.k00ntz.aoc

import com.google.common.graph.GraphBuilder
import com.k00ntz.aoc.utils.parseFile

val day7FileName = "7-2018.txt"

val day7Regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".toRegex()

data class CharLine(val preStep: Char, val postStep: Char)

val day7ParseFn = { s: String ->
    day7Regex.matchEntire(s)?.destructured?.let { (prStep: String, psStep: String) ->
        CharLine(prStep[0], psStep[0])
    }!!
}

fun main(args: Array<String>) {
    println(day7(parseFile(day7FileName, day7ParseFn)))
}

fun day7(lines: List<CharLine>): String {
    val graph = GraphBuilder.directed().build<Char>()
    lines.forEach { graph.putEdge(it.preStep, it.postStep) }
    var inNodes = lines.map { it.preStep }.minus(lines.map { it.postStep }).toSortedSet().toMutableList()
    val done = mutableListOf<Char>()
    while (inNodes.isNotEmpty()) {
        val node = inNodes.first()
        done.add(node)
        inNodes = inNodes.drop(1).plus(graph.successors(node).filter { done.containsAll(graph.predecessors(it)) })
            .toSortedSet().toMutableList()

    }
    return done.fold("") { acc: String, c: Char -> acc + c }

}


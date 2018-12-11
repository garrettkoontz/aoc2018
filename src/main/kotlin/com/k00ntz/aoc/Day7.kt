package com.k00ntz.aoc

import com.google.common.graph.GraphBuilder
import com.google.common.graph.ImmutableGraph
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
    println(day7part2(parseFile(day7FileName, day7ParseFn)))
}

fun day7(lines: List<CharLine>): String {
    val graph = buildGraph(lines)
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

private fun buildGraph(lines: List<CharLine>): ImmutableGraph<Char> {
    val graph = GraphBuilder.directed().build<Char>()
    lines.forEach { graph.putEdge(it.preStep, it.postStep) }
    return ImmutableGraph.copyOf(graph)
}

data class Worker(val task: Char, val finishTime: Int)

fun day7part2(lines: List<CharLine>, workerSize: Int = 5, minTaskLength: Int = 60): Int {
    val graph = buildGraph(lines)
    var inNodes = lines.map { it.preStep }.minus(lines.map { it.postStep }).toSortedSet().toMutableList()
    val done = mutableListOf<Char>()
    val getTaskLength = getTaskLength(minTaskLength)
    val working = arrayOfNulls<Worker>(workerSize)
    val start =
        inNodes.take(workerSize).map { Worker(it, getTaskLength(it)) }.also { inNodes.removeAll(it.map { it.task }) }
    start.take(workerSize).forEachIndexed { index, worker -> working.set(index, worker) }
    var time = 0
    while (inNodes.isNotEmpty() || !working.all { it == null }) {
        if (inNodes.isEmpty() || working.none { it == null }) { // we need more work to do or we need a worker to do work
            //next to finish
            val completedTask = working.minBy { it?.finishTime ?: Integer.MAX_VALUE }!!
            //jump time
            time = completedTask.finishTime
            done.add(completedTask.task)
            val indx = working.indexOf(completedTask)
            inNodes = newInNodes(
                working,
                indx,
                inNodes,
                graph.successors(completedTask.task).filter { done.containsAll(graph.predecessors(it)) },
                time,
                getTaskLength
            )
        } else { // we have enough work and a free worker
            val indx = working.indexOf(null)
            inNodes = newInNodes(working, indx, inNodes, emptyList(), time, getTaskLength)
        }
    }


    return time
}

private fun newInNodes(
    working: Array<Worker?>,
    indx: Int,
    inNodes: MutableList<Char>,
    newChars: List<Char>,
    time: Int,
    getTaskLength: (Char) -> Int
): MutableList<Char> {
    var inNodes1 = inNodes
    inNodes1 = inNodes1.plus(newChars)
        .toSortedSet().toMutableList()
    if (inNodes1.isNotEmpty()) {
        val nextChar = inNodes1.first()
        inNodes1 = inNodes1.drop(1).toMutableList()
        working[indx] = Worker(nextChar, time + getTaskLength(nextChar))
    } else {
        working[indx] = null
    }
    return inNodes1
}

fun getTaskLength(minSize: Int) =
    { c: Char -> minSize + c.toInt() - 'A'.toInt() + 1 }

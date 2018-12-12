package com.k00ntz.aoc

import com.k00ntz.aoc.utils.LinkedNode
import com.k00ntz.aoc.utils.parseLine

val day9FileName = "9-2018.txt"

val day9ParseFn = { s: String ->
    "([0-9]+) players; last marble is worth ([0-9]+) points".toRegex().matchEntire(s)
        ?.destructured?.let { (players: String, points: String) ->
        Pair(players.toInt(), points.toInt())
    }!!
}


fun main(args: Array<String>) {
    println(day9(parseLine(day9FileName, day9ParseFn)))
    println(day9part2(parseLine(day9FileName, day9ParseFn)))
}

fun day9part2(input: Pair<Int, Int>): Long {
    return Board(input.first, input.second * 100).play().max()!!
}

fun day9(input: Pair<Int, Int>): Long {
    return Board(input.first, input.second).play().max()!!
}

class Board(
    private val numPlayers: Int,
    private val limit: Int
) {

    private val specialNumber = 23
    private val forwardMovement = 1
    private val backwardMovement = -7

    fun play(): Array<Long> {
        val scores: Array<Long> = (1..numPlayers).map { 0L }.toTypedArray()
        val firstNode: LinkedNode<Int> = LinkedNode<Int>(0, null, null)
        firstNode.nextNode = firstNode
        firstNode.prevNode = firstNode
        val board = firstNode
        val boardSize = 1
        val scoresList = (1..limit).fold(Triple(board, boardSize, emptyList<Int>())) { acc, i ->
            if (i % specialNumber == 0) {
                val (removed, rest) = acc.first.removeAt(backwardMovement)
                Triple(rest, acc.second - 1, acc.third.plus(removed.value))
            } else {
                val newBoard = acc.first.nodeAt(forwardMovement).add(i)
                Triple(newBoard, acc.second + 1, acc.third)
            }
        }
        scoresList.third.foldIndexed(scores) { index: Int, scores: Array<Long>, value: Int ->
            scores[(index - 1 + numPlayers) % numPlayers] += ((index + 1) * specialNumber) + value.toLong()
            scores
        }

        return scores
    }
}



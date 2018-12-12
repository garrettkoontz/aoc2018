package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseLine
import sun.awt.image.ImageWatched
import java.lang.Math.abs
import java.util.*

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

fun day9part2(input: Pair<Int, Int>): Any? {
    return Board(input.first, input.second * 100).play().max()!!
}

fun day9(input: Pair<Int, Int>): Int {
    return Board(input.first, input.second).play().max()!!
}

class LinkedNode(val value: Int, var prevNode: LinkedNode?, var nextNode: LinkedNode?) {

    fun nodeAt(distance: Int): LinkedNode {
        return when {
            distance == 1 -> nextNode!!
            distance == -1 -> prevNode!!
            distance < 0 -> {
                (0..abs(distance)).fold(this) { acc, _ -> acc.prevNode!! }
            }
            distance > 0 -> {
                (0..distance).fold(this) { acc, _ -> acc.nextNode!! }
            }
            else -> this
        }
    }

    fun removeAt(distance: Int): LinkedNode {
        val removedNode = nodeAt(distance)
        removedNode.prevNode!!.nextNode = removedNode.nextNode
        removedNode.nextNode!!.prevNode = removedNode.prevNode
        return removedNode.nextNode!!
    }

    fun add(i: Int): LinkedNode {
        val ln = LinkedNode(i, this, this.nextNode)
        this.nextNode!!.prevNode = ln
        this.nextNode = ln
        return ln
    }

}

class Board(
    private val numPlayers: Int,
    private val limit: Int
) {

    private val specialNumber = 23
    private val forwardMovement = 1
    private val backwardMovement = -7

    fun play(): Array<Int> {
        Collections.rotate()
        val scores: Array<Int> = (1..numPlayers).map { 0 }.toTypedArray()
        val firstNode = LinkedNode(0, null, null)
        firstNode.nextNode = firstNode
        firstNode.prevNode = firstNode
        var board = firstNode
        var boardSize = 1
        for (i in (1..limit)) {
            if (i % specialNumber == 0) {
                board = board.removeAt(backwardMovement)
                scores[(i - 1 + numPlayers) % numPlayers] += board.value + i
                boardSize--
            } else {
                board = board.nodeAt(forwardMovement).add(i)
                boardSize++
            }
        }
        return scores
    }
}



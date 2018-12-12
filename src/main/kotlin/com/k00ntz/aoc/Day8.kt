package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseLine
import java.util.*

val day8FileName = "8-2018.txt"

val day8ParseFn = { s: String -> s.split(" ").map { it.toInt() } }

fun main(args: Array<String>) {
    println(day8(parseLine(day8FileName, day8ParseFn)))
    println(day8part2(parseLine(day8FileName, day8ParseFn)))
}

fun day8part2(ints: List<Int>): Int {
    val node = Node.buildFromInts(ints)
    return valueOfNode(node)
}

fun valueOfNode(node: Node): Int = if (node.children.isNotEmpty()) {
    node.metadataEntries.map {
        if (node.children.elementAtOrNull(it - 1) != null) valueOfNode(node.children[it - 1]) else 0
    }
        .sum()
} else {
    node.metadataEntries.sum()
}


fun day8(ints: List<Int>): Int {
    val node = Node.buildFromInts(ints)
    return checkSum(node)
}

fun checkSum(node: Node): Int {
    return checkSum(node.metadataEntries.sum(), node.children)
}

tailrec fun checkSum(i: Int, nodes: List<Node>): Int {
    return if (nodes.isEmpty()) i else checkSum(
        i + nodes.flatMap { it.metadataEntries }.sum(),
        nodes.flatMap { it.children })
}


data class Node(val metadataEntries: List<Int>, val children: List<Node>) {

    data class NodeParse(val childCount: Int, val metaEntriesCount: Int, val children: MutableList<Node>)

    companion object {
        fun buildFromInts(ints: List<Int>): Node {
            val ints1 = ArrayDeque(ints)
            val deque: Deque<NodeParse> = ArrayDeque()
            val head = NodeParse(1, 0, mutableListOf())
            deque.offerFirst(head)
            while (ints1.isNotEmpty()) {
                val numChildren = ints1.pollFirst()
                if (numChildren == 0) {
                    val metaEntries = (1..ints1.pollFirst()).map { ints1.pollFirst() }
                    deque.peekFirst().children.add(Node(metaEntries, emptyList()))

                } else {
                    val nodeParse = NodeParse(numChildren, ints1.pollFirst(), mutableListOf())
                    deque.offerFirst(nodeParse)
                }
                unwind(deque, ints1)
            }
            unwind(deque, ints1)
            return deque.first.children.first()
        }

        private fun unwind(deque: Deque<NodeParse>, ints1: ArrayDeque<Int>) {
            while (deque.peekFirst().children.size == deque.peekFirst().childCount && deque.size > 1) {
                val doneChild = deque.pollFirst()
                deque.peekFirst().children.add(
                    Node(
                        (1..doneChild.metaEntriesCount).map { ints1.pollFirst() },
                        doneChild.children.toList()
                    )
                )
            }
        }

    }

}



package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseFile
import java.util.*

val day8FileNmae = "8-2018.txt"

val day8ParseFn = { s: String -> s.split(" ").map { it.toInt() } }

fun main(args: Array<String>) {
    println(day8(parseFile(day8FileNmae, day8ParseFn).first()))
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


class Node(val metadataEntries: List<Int>, val children: List<Node>) {

    class NodeParse(val childCount: Int, val metaEntriesCount: Int, val children: MutableList<Node>)

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
                    deque.offerFirst(NodeParse(ints1.pollFirst(), ints1.pollFirst(), mutableListOf()))
                }
                unwind(deque, ints1)
            }
            unwind(deque, ints1)
            return deque.first.children.first()
        }

        private fun unwind(deque: Deque<NodeParse>, ints1: ArrayDeque<Int>) {
            while (deque.peekFirst().children.size == deque.peekFirst().childCount) {
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



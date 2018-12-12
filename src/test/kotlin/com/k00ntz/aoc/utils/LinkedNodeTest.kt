package com.k00ntz.aoc.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class LinkedNodeTest {

    @Test
    fun nodeAt() {
        val nd = LinkedNode(0)
        val nd1 = nd.add(1)
        val nd2 = nd1.add(2)
        val nd3 = nd2.add(3)
        assertEquals(nd3, nd.nodeAt(-1))
        assertEquals(nd1, nd.nodeAt(1))
        assertEquals(nd2, nd.nodeAt(2))
        assertEquals(nd3, nd.nodeAt(3))
        assertEquals(nd1, nd3.nodeAt(2))

    }

    @Test
    fun removeAt() {
        val nd = LinkedNode(0)
        val nd1 = nd.add(1)
        val nd2 = nd1.add(2)
        val nd3 = nd2.add(3)
        val (removed, rest) = nd2.removeAt(2)
        assertEquals(nd.value, removed.value)
        assertEquals(nd1, nd3.nextNode)
        assertEquals(nd2, nd3.prevNode)
        assertEquals(nd3, nd1.prevNode)
        assertEquals(nd2, nd1.nextNode)
    }

    @Test
    fun add() {
    }

    @Test
    fun toIntList() {
    }
}
package com.k00ntz.aoc.utils

import java.util.*
import kotlin.streams.toList
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

inline fun <T : Any> parseFile(fileName: String, crossinline parsefn: (String) -> T): List<T> =
    ClassLoader.getSystemResourceAsStream(fileName).use {
        it.bufferedReader().lines().map { parsefn(it) }.toList()
    }

inline fun <T: Any> parseLine(fileName: String, crossinline parsefn: (String) -> T): T =
    ClassLoader.getSystemResourceAsStream(fileName).use {
        it.bufferedReader().lines().map { parsefn(it) }.findFirst()
    }.get()

//fun <T> cartesian(c1: Collection<T>, c2: Collection<T> = c1): List<Pair<T, T>> =
//    c1.flatMap { a -> c2.map { b -> Pair(a, b) } }

fun <T> cartesian(c1: Iterable<T>, c2: Iterable<T> = c1): List<Pair<T, T>> =
    c1.flatMap { a -> c2.map { b -> Pair(a, b) } }

typealias Point = Pair<Int, Int>

fun Point.x(): Int =
    this.first

fun Point.y(): Int =
    this.second


fun ccw(p1: Point, p2: Point, p3: Point) =
    (p2.x() - p1.x()) * (p3.y() - p1.y()) - (p2.y() - p1.y()) * (p3.x() - p1.x())

fun convexHull(pts: List<Point>): List<Point> {
    val n = pts.size
    val minPt = pts.minBy { it.second }!!
    val parts = pts.partition { it.y() == minPt.y() }
    val others = parts.second.sortedBy { (minPt.x().toDouble() - it.x().toDouble()) / (it.y().toDouble() - minPt.y().toDouble()) }
    val points = listOf(minPt) + parts.first.filter { it.x() - minPt.x() > 0 } +
            others + parts.first.filter { it.x() - minPt.x() < 0 }
    val stack = Stack<Point>()
    stack.push(points[0])
    stack.push(points[1])
    for (i in (2..(n - 1))) {
        val vl = ccw(stack.elementAt(stack.size - 2), stack.peek(), points[i])
        while (stack.size >= 2 &&
            ccw(stack.elementAt(stack.size - 2), stack.peek(), points[i]) <= 0
        ) {
            stack.pop()
        }
        stack.push(points[i])

    }
    return stack.toList()
}

fun <A, B>Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}
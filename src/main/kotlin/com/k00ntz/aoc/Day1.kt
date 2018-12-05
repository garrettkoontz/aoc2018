package com.k00ntz.aoc

val FILE_NAME = "1-2018.txt"

class Ring<T: Any>(private val list: List<T>, private val size: Int = list.size, private var start: Int = 0) : Iterable<T>{
    override fun iterator(): Iterator<T>  = object : Iterator<T> {
        override fun hasNext(): Boolean = true

        override fun next(): T  = list[start % size].also{start++}

    }
}


fun day1(freqs: List<Long>): Long =
    freqs.fold(0L) { a, c -> a + c }

fun day1part2(freqs: List<Long>): Long? {
    val ring = Ring(freqs)
    var pair = Pair(0L, setOf(0L))
    for (l in ring){
        val pair2 = op(pair, l)
        if(pair2.second.size == pair.second.size){
            return pair2.first
        } else {
            pair = pair2
        }
    }
    return null
}

fun op(accum: Pair<Long,Set<Long>>, next: Long): Pair<Long,Set<Long>> =
    (accum.first + next).let{Pair(it, accum.second.plus(it))}


fun main(args: Array<String>) {
    val parsefn = { i: String -> i.toLong()}
    println("Day1 part 1: ${day1(parseFile(parsefn = parsefn))}")
    println("Day1 part 2: ${day1part2(parseFile(parsefn = parsefn))}")
}
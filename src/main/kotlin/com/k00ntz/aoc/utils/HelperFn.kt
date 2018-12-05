package com.k00ntz.aoc.utils

import kotlin.streams.toList

inline fun <T : Any> parseFile(fileName: String, crossinline parsefn: (String) -> T): List<T> =
    ClassLoader.getSystemResourceAsStream(fileName).use {
        it.bufferedReader().lines().map { parsefn(it) }.toList()
    }

fun <T> cartesian(c1: Collection<T>, c2: Collection<T> = c1): List<Pair<T, T>> =
    c1.flatMap { a -> c2.map { b -> Pair(a, b) } }
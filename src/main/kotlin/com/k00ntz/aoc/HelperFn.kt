package com.k00ntz.aoc

import kotlin.streams.toList

fun <T: Any> parseFile(fileName: String = FILE_NAME, parsefn: (String) -> T): List<T> =
    ClassLoader.getSystemResourceAsStream(fileName).use {
        it.bufferedReader().lines().map { parsefn(it) }.toList()
    }
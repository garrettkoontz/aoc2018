package com.k00ntz.aoc

import com.k00ntz.aoc.utils.Grid
import com.k00ntz.aoc.utils.Particle
import com.k00ntz.aoc.utils.parseFile
import com.k00ntz.aoc.utils.toGrid

val day10FileName = "10-2018.txt"


//position=< 9,  1> velocity=< 0,  2>
val day10Regex = "position=<(.*), (.*)> velocity=<(.*), (.*)>".toRegex()

val day10ParseFn = { s: String ->
    day10Regex.matchEntire(s)?.destructured?.let { (posX: String, posY: String, velX: String, velY: String) ->
        Particle(
            position = Pair(posX.trim().toInt(), posY.trim().toInt()),
            velocity = Pair(velX.trim().toInt(), velY.trim().toInt())
        )
    }!!

}

fun main(args: Array<String>) {
    println(day10(parseFile(day10FileName, day10ParseFn)))
}

fun day10(particles: List<Particle>): String {
    var particles1 = particles
    while (!saysSomething(particles1.toGrid())) {
        particles1 = particles1.map { it.move() }
    }
    return particles1.toGrid().toString()
}

val textsize = 3

fun saysSomething(grid: Grid): Boolean {
    println(grid)
    return grid.grid.fold(false) { acc, chars -> acc || chars.containsRunGreaterThan(textsize, grid.pointChar) }
}

private fun Array<Char>.containsRunGreaterThan(textsize: Int, char: Char): Boolean {
    if (this.contains('#')) {
        for (it in (0 until this.size - textsize)) {
            val slice = this.slice(it..it + textsize)
            if (setOf(char).containsAll(slice.toSet()))
                return true
        }
    }
    return false
}


package com.k00ntz.aoc

import com.k00ntz.aoc.utils.*
import java.lang.Math.max
import java.lang.Math.min
import kotlin.system.measureTimeMillis

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
    println(day10part2(parseFile(day10FileName, day10ParseFn)))
}

fun day10part2(particles: List<Particle>): Int {
    var particles1 = particles
    var area1 = area(particles1)
    var particles2 = particles.map { it.move() }
    var i = 1
    var area2 = area(particles2)
    while (area1 > area2) {
        particles1 = particles2
        area1 = area2
        particles2 = particles1.map { it.move() }
        i++
        area2 = area(particles2)
    }
    return i -1

}

fun day10(particles: List<Particle>): String {
    var particles1 = particles
    var area1 = area(particles1)
    var particles2 = particles.map { it.move() }
    var area2 = area(particles2)
    while (area1 > area2) {
        particles1 = particles2
        area1 = area2
        particles2 = particles1.map { it.move() }
        area2 = area(particles2)
    }
    return Grid(particles1).toString()

}

private fun area(stars: List<Particle>) = stars
    .fold(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)) { (minX, minY, maxX, maxY), s ->
        listOf(min(minX, s.x), min(minY, s.y), max(maxX, s.x), max(maxY, s.y))
    }
    .let { (minX, minY, maxX, maxY) -> (maxX - minX).toLong() * (maxY - minY).toLong() }

fun day10old(particles: List<Particle>): String {
    var grid = Grid(particles)
    var i = 0
    while (!saysSomething(grid)) {
        grid = grid.tick()
        i++
        if (i % 1000 == 0) println("done $i ticks")
    }
    return grid.toString()
}

val textsize = 7

fun saysSomething(grid: Grid): Boolean {
//    println(grid)
//    println()
    return grid.containsRunGreaterThan(textsize)
}




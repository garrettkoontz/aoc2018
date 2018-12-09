package com.k00ntz.aoc

import com.k00ntz.aoc.utils.parseFile
import java.time.LocalDateTime
import kotlin.text.Regex.Companion.escape

val day4FileName = "4-2018.txt"

val day4Regex = ("${escape("[")}([0-9]+)-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)${escape("]")}" +
        " (.*)").toRegex()

val actionRegex = "(Guard #([0-9]+) begins shift|falls asleep|wakes up)".toRegex()

enum class Action {
    SLEEPS,
    WAKES,
    STARTS
}

fun getAction(s: String): Action? =
    when (s) {
        "falls asleep" -> Action.SLEEPS
        "wakes up" -> Action.WAKES
        else -> Action.STARTS
    }


data class ParseLine(val dateTime: LocalDateTime, var id: Int?, var action: Action?)
data class Line(val dateTime: LocalDateTime, val id: Int, val action: Action) {
    constructor(pline: ParseLine) : this(pline.dateTime, pline.id!!, pline.action!!)
}

val day4ParseFn = { s: String ->
    day4Regex.matchEntire(s)
        ?.destructured?.let { (year: String, month: String, day: String, hour: String, minute: String,
                                      text: String)
        ->
        val out: Pair<Int?, Action?> =
            actionRegex.matchEntire(text)?.destructured?.let { (action: String, id: String) ->
                if (id.isBlank()) Pair(null, getAction(action)) else Pair(id.toInt(), Action.STARTS)
            }!!
        ParseLine(
            LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt()),
            out.first,
            out.second
        )
    }!!
}


fun main(args: Array<String>) {
    println(day4(parseFile(day4FileName, day4ParseFn)))
    println(day4part2(parseFile(day4FileName, day4ParseFn)))
}

fun day4part2(lines: List<ParseLine>): Int {
    val ordered = lines.sortedBy { it.dateTime }
    val lns = fill(ordered)
    val byid = lns.groupBy { it.id }
    val maps = byid.mapValues { sleepMap(it.value) }
    val sleepMins = maps.mapValues { v: Map.Entry<Int, Map<Int, Set<Int>>> ->
        v.value.map { (k, v) -> Pair(k, v.size) }.toMap()
    }
    val maxSleepId: Map.Entry<Int,Map<Int,Int>> = sleepMins.maxBy { it.value.values.max()!! }!!
    val maxMinute = maxSleepId.value.maxBy { it.value }!!
    return maxSleepId.key * maxMinute.key
}

fun day4(lines: List<ParseLine>): Int {
    val ordered = lines.sortedBy { it.dateTime }
    val lns = fill(ordered)
    val byid = lns.groupBy { it.id }
    val maps = byid.mapValues { sleepMap(it.value) }
    val sleepMins = maps.mapValues { v: Map.Entry<Int, Map<Int, Set<Int>>> ->
        v.value.map { (k, v) -> Pair(k, v.size) }.toMap()
    }
    val maxSleepId: Map.Entry<Int,Map<Int,Int>> = sleepMins.maxBy { it.value.values.sum() }!!
    val maxMinute = maxSleepId.value.maxBy { it.value }!!
    return maxSleepId.key * maxMinute.key
}

fun sleepMap(lines: List<Line>): Map<Int, Set<Int>> {
    val sleepMap = (0 until 60).associate { Pair(it, mutableSetOf<Int>()) }
    val sleepTimes = lines.filter { it.action == Action.SLEEPS }
    val wakeTimes = lines.filter { it.action == Action.WAKES }
    sleepTimes.zip(wakeTimes).forEach { (sleepLine, wakeLine) ->
        (sleepLine.dateTime.minute until wakeLine.dateTime.minute).forEach {
            sleepMap[it]?.add(sleepLine.dateTime.dayOfYear)
        }
    }
    return sleepMap
}

fun fill(lines: List<ParseLine>): List<Line> {
    var id = lines.first().id!!
    for (line in lines.drop(1)) {
        if (line.id == null)
            line.id = id
        else id = line.id!!

    }
    return lines.map { Line(it) }
}

//[1518-11-01 00:00] Guard #10 begins shift
//[1518-11-01 00:05] falls asleep
//[1518-11-01 00:25] wakes up
//[1518-11-01 00:30] falls asleep
//[1518-11-01 00:55] wakes up
//[1518-11-01 23:58] Guard #99 begins shift
//[1518-11-02 00:40] falls asleep
//[1518-11-02 00:50] wakes up
//[1518-11-03 00:05] Guard #10 begins shift
//[1518-11-03 00:24] falls asleep
//[1518-11-03 00:29] wakes up
//[1518-11-04 00:02] Guard #99 begins shift
//[1518-11-04 00:36] falls asleep
//[1518-11-04 00:46] wakes up
//[1518-11-05 00:03] Guard #99 begins shift
//[1518-11-05 00:45] falls asleep
//[1518-11-05 00:55] wakes up
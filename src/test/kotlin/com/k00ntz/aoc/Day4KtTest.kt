package com.k00ntz.aoc

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class Day4KtTest {


    val teststrs = listOf(
        "[1518-11-01 00:00] Guard #10 begins shift",
        "[1518-11-01 00:05] falls asleep",
        "[1518-11-01 00:25] wakes up",
        "[1518-11-01 00:30] falls asleep",
        "[1518-11-01 00:55] wakes up",
        "[1518-11-01 23:58] Guard #99 begins shift",
        "[1518-11-02 00:40] falls asleep",
        "[1518-11-02 00:50] wakes up",
        "[1518-11-03 00:05] Guard #10 begins shift",
        "[1518-11-03 00:24] falls asleep",
        "[1518-11-03 00:29] wakes up",
        "[1518-11-04 00:02] Guard #99 begins shift",
        "[1518-11-04 00:36] falls asleep",
        "[1518-11-04 00:46] wakes up",
        "[1518-11-05 00:03] Guard #99 begins shift",
        "[1518-11-05 00:45] falls asleep",
        "[1518-11-05 00:55] wakes up"
    )

    @Test
    fun day4Test(){

        val plns = teststrs.map(day4ParseFn)
        val ans = day4(plns)
        assertEquals(240, ans)
    }

    @Test
    fun day4part2Test(){
        val plns = teststrs.map(day4ParseFn)
        val ans = day4part2(plns)
        assertEquals(4455, ans)
    }

    @Test
    fun getDay4ParseFn() {
        val strs = listOf(
            "[1518-11-05 00:45] falls asleep",
            "[1518-11-05 00:03] Guard #99 begins shift",
            "[1518-11-05 00:55] wakes up"
        )
        val lines = strs.map(day4ParseFn).toSet()
        val expected = setOf(
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 45), null, Action.SLEEPS),
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 3), 99, Action.STARTS),
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 55), null, Action.WAKES)
        )

        assertEquals(expected, lines)

    }

    @Test
    fun fillTest() {
        val lines = listOf(
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 3), 99, Action.STARTS),
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 45), null, Action.SLEEPS),
            ParseLine(LocalDateTime.of(1518, 11, 5, 0, 55), null, Action.WAKES),
            ParseLine(LocalDateTime.of(1518, 11, 6, 0, 3), 100, Action.STARTS)
            )

        val filledLines = fill(lines)
        assertEquals(
            setOf(
                Line(LocalDateTime.of(1518, 11, 5, 0, 3), 99, Action.STARTS),
                Line(LocalDateTime.of(1518, 11, 5, 0, 45), 99, Action.SLEEPS),
                Line(LocalDateTime.of(1518, 11, 5, 0, 55), 99, Action.WAKES),
                Line(LocalDateTime.of(1518, 11, 6, 0, 3), 100, Action.STARTS)
            )
            , filledLines.toSet()
        )
    }
}
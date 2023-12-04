package me.mattco.aoc2023

import kotlin.math.pow

class Day4 : Day() {
    private val input by lazy {
        inputLines.map {
            val (winningNumbers, actualNumbers) = it.substringAfter(": ").split("|")
            Card(
                winningNumbers.split(" ").filter { it.isNotBlank() }.mapTo(mutableSetOf()) { it.toInt() },
                actualNumbers.split(" ").filter { it.isNotBlank() }.mapTo(mutableSetOf()) { it.toInt() },
            )
        }
    }

    override fun part1() = input.sumOf { (1 shl it.matches()) shr 1 }

    override fun part2(): Any {
        val counts = input.mapTo(mutableListOf()) { 1 }
        counts.forEachIndexed { index, count ->
            for (i in 1..input[index].matches())
                counts[index + i] += count
        }
        return counts.sum()
    }

    data class Card(val winningNumbers: Set<Int>, val actualNumbers: Set<Int>) {
        fun matches() = actualNumbers.count { it in winningNumbers }
    }
}

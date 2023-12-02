package me.mattco.aoc2023

import kotlin.math.max

class Day2 : Day() {
    private val input by lazy {
        inputLines
            .map { it.substringAfter(": ") }
            .map { line ->
                line.split("; ").map { hand ->
                    hand.split(", ").map { cubes ->
                        val (amount, color) = cubes.split(' ')
                        CubeCount(amount.toInt(), Color.entries.first { it.name.lowercase() == color })
                    }
                }
            }
    }

    override fun part1(): Any {
        val limits = mapOf(
            Color.Red to 12,
            Color.Green to 13,
            Color.Blue to 14,
        )

        return input.withIndex().filter {
            it.value.all { counts ->
                counts.all { (amount, color) -> amount <= limits[color]!! }
            }
        }.sumOf { it.index + 1 }
    }

    override fun part2(): Any {
        return input.sumOf { game ->
            val maximums = mutableMapOf(
                Color.Red to 0,
                Color.Green to 0,
                Color.Blue to 0,
            )

            for (hand in game) {
                for ((amount, color) in hand)
                    maximums[color] = max(maximums[color]!!, amount)
            }

            maximums.values.fold(1) { acc: Int, i -> acc * i }
        }
    }

    private data class CubeCount(val amount: Int, val color: Color)

    private enum class Color {
        Red,
        Green,
        Blue
    }
}

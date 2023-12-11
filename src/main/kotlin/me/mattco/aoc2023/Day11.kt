package me.mattco.aoc2023

import kotlin.math.abs

class Day11 : Day() {
    private fun input() = inputLines.mapIndexedTo(mutableListOf()) { y, line ->
        line.mapIndexedTo(mutableListOf()) { x, ch ->
            Pixel(x.toLong(), y.toLong(), ch == '#')
        }
    }

    override fun part1() = solve(2)

    override fun part2() = solve(1_000_000)

    private fun solve(expansionFactor: Int): Long {
        val input = this.input()

        var yShift = 0
        for (row in input) {
            if (row.none(Pixel::isGalaxy)) {
                yShift += expansionFactor - 1
            } else {
                row.forEach { it.y += yShift }
            }
        }

        var xShift = 0
        for (x in input[0].indices) {
            if (input.none { it[x].isGalaxy }) {
                xShift += expansionFactor - 1
            } else {
                input.forEach { it[x].x += xShift }
            }
        }

        return input.flatten().filter(Pixel::isGalaxy).combinations().sumOf { (a, b) ->
            abs(b.x - a.x) + abs(b.y - a.y)
        }
    }

    private class Pixel(var x: Long, var y: Long, val isGalaxy: Boolean)
}

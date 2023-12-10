package me.mattco.aoc2023

class Day9 : Day() {
    private val input = inputLines.map { it.split(' ').map(String::toLong) }

    override fun part1() = solve { prev, list -> prev + list.last() }

    override fun part2() = solve { prev, list -> list.first() - prev }

    private fun solve(reducer: (Long, List<Long>) -> Long): Long {
        return input.sumOf { firstLine ->
            generateSequence(firstLine, List<Long>::diff)
                .takeWhile { line -> line.any { it != 0L } }
                .toList()
                .asReversed()
                .fold(0L, reducer)
        }
    }
}

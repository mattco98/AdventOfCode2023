package me.mattco.aoc2023

class Day6 : Day() {
    private val input1 = listOf(
        Race(47, 282),
        Race(70, 1079),
        Race(75, 1147),
        Race(66, 1062),
    )

    private val input2 = listOf(Race(47707566L, 282107911471062L))

    override fun part1() = calculateWinningCombinations(input1)

    override fun part2() = calculateWinningCombinations(input2)

    private fun calculateWinningCombinations(input: List<Race>) = input.map { race ->
        (0..race.time).count { ((race.time - it) * it) > race.record }
    }.mul()

    data class Race(val time: Long, val record: Long)
}

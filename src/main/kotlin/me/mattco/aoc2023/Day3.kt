package me.mattco.aoc2023

class Day3 : Day() {
    private val numberRegex = """\d+""".toRegex()
    private val symbolRegex = """[^\d.]""".toRegex()

    private val parts = buildList {
        inputLines.forEachIndexed { lineIndex, line ->
            numberRegex.findAll(line).map { it.groups[0]!! }.forEach { group ->
                add(Part(group.value, x = group.range.first, y = lineIndex))
            }
        }
    }

    private val symbols = buildList {
        inputLines.forEachIndexed { lineIndex, line ->
            symbolRegex.findAll(line).map { it.groups[0]!! }.forEach { group ->
                add(Symbol(group.value.single(), x = group.range.first, y = lineIndex))
            }
        }
    }

    init {
        val partsByY = parts.groupBy { it.y }

        for (symbol in symbols) {
            for (y in symbol.y.rangeAround(1, bounds = inputLines.indices)) {
                for (part in partsByY[y] ?: continue) {
                    if (part.x - 1 > symbol.x)
                        break

                    if (part.x + part.value.length >= symbol.x) {
                        part.adjacentToASymbol = true
                        symbol.adjacentParts.add(part)
                    }
                }
            }
        }
    }

    override fun part1() =  parts.filter(Part::adjacentToASymbol).sumOf { it.value.toInt() }

    override fun part2() = symbols.mapNotNull(Symbol::gearRatio).sum()

    private data class Part(val value: String, val x: Int, val y: Int, var adjacentToASymbol: Boolean = false)

    private data class Symbol(val char: Char, val x: Int, val y: Int) {
        val adjacentParts = mutableListOf<Part>()

        fun gearRatio() = if (char == '*' && adjacentParts.size == 2) {
            adjacentParts[0].value.toInt() * adjacentParts[1].value.toInt()
        } else null
    }
}

package me.mattco.aoc2023

class Day1 : Day() {
    override fun part1(): Any {
        return inputLines.map { line ->
            line.filter(Char::isDigit).let { it.first().toString() + it.last() }
        }.sumOf { it.toInt() }
    }

    override fun part2(): Any {
        val replacements = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        fun String.getDigitAt(index: Int): String? {
            if (this[index].isDigit())
                return this[index].toString()

            val substr = this.substring(index)
            for ((replacement, value) in replacements) {
                if (substr.startsWith(replacement))
                    return value
            }

            return null
        }

        return inputLines.map {  line ->
            val firstDigit = line.indices.asSequence().mapNotNull(line::getDigitAt).first()
            val lastDigit = line.indices.reversed().asSequence().mapNotNull(line::getDigitAt).first()
            firstDigit + lastDigit
        }.sumOf { it.toInt() }
    }
}

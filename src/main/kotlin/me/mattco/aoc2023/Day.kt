package me.mattco.aoc2023

abstract class Day {
    private val inputFile by lazy {
        val fileName = "/input/day${this::class.simpleName!!.filter(Char::isDigit)}"
        this::class.java.getResourceAsStream(fileName)?.bufferedReader() ?: error("No such resource file: $fileName")
    }

    protected val inputText by lazy { inputFile.readText() }

    protected val inputLines by lazy { inputFile.readLines().dropLastWhile { it.isEmpty() } }

    abstract fun part1(): Any
    abstract fun part2(): Any

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val activeDay = Day1::class.java
            val instance: Day = activeDay.getDeclaredConstructor().newInstance()
            val header = "============== ${activeDay.simpleName} =============="
            println(header)
            println("part 1: ${instance.part1()}")
            println("part 2: ${instance.part2()}")
            println("=".repeat(header.length))
        }
    }
}

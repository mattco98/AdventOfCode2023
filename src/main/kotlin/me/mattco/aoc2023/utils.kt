package me.mattco.aoc2023

import kotlin.math.max
import kotlin.math.min

fun Int.rangeAround(n: Int, bounds: IntRange) =
    max(this - n, bounds.min())..min(this + n, bounds.max())

fun LongRange.intersection(other: LongRange) = when {
    endInclusive < other.start || start > other.endInclusive -> null
    else -> max(start, other.start)..min(endInclusive, other.endInclusive)
}

fun Collection<Int>.mul() = fold(1) { p, c -> p * c }
fun Collection<Long>.mul() = fold(1L) { p, c -> p * c }
fun Collection<Float>.mul() = fold(1.0f) { p, c -> p * c }
fun Collection<Double>.mul() = fold(1.0) { p, c -> p * c }

fun <T> Iterable<T>.eachCount() = groupingBy { it }.eachCount()

fun unreachable(): Nothing = error("unreachable")

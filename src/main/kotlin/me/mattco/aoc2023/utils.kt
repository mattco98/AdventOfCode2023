package me.mattco.aoc2023

import java.util.LinkedList
import kotlin.math.max
import kotlin.math.min

fun unreachable(): Nothing = error("unreachable")

fun Int.rangeAround(n: Int, bounds: IntRange) =
    max(this - n, bounds.min())..min(this + n, bounds.max())

fun LongRange.size() = endInclusive - start + 1

fun LongRange.intersection(other: LongRange) = when {
    endInclusive < other.first || start > other.last -> null
    else -> max(start, other.first)..min(endInclusive, other.last)
}

fun LongRange.union(other: LongRange) =
    (min(start, other.first)..max(last, other.last)).takeIf {
        it.size() <= size() + other.size()
    }

fun <T> List<T>.combine(combiner: (T, T) -> T?): List<T> {
    val list = LinkedList(this)
    
    var i = 0
    while (i < list.lastIndex) {
        val combined = combiner(list[i], list[i + 1])
        if (combined != null) {
            list[i] = combined
            list.removeAt(i + 1)
        } else {
            i += 1
        }
    }

    return list.toList()
}

fun Collection<Int>.mul() = fold(1) { p, c -> p * c }
fun Collection<Long>.mul() = fold(1L) { p, c -> p * c }
fun Collection<Float>.mul() = fold(1.0f) { p, c -> p * c }
fun Collection<Double>.mul() = fold(1.0) { p, c -> p * c }

fun <T> Iterable<T>.eachCount() = groupingBy { it }.eachCount()

fun Long.factors(): List<Long> {
    check(this > 0)
    if (this == 1L) return listOf(1)

    for (n in 2L..(this / 2L)) {
        if (this % n == 0L)
            return (this / n).factors() + listOf(n)
    }

    return listOf(this)
}

fun gcd(a: Long, b: Long) = a.factors().toSet().intersect(b.factors().toSet()).max()

fun lcm(a: Long, b: Long) = (a * b) / gcd(a, b)

fun List<Long>.diff() = zip(drop(1)).map { it.second - it.first }

enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1),
    Down(0, 1),
    Left(-1, 0),
    Right(1, 0);

    fun apply(x: Int, y: Int) = (x + dx) to (y + dy)
}

fun <T> List<T>.combinations(): List<Pair<T, T>> {
    val elements = mutableListOf<Pair<T, T>>()

    for (i in indices) {
        for (j in (i + 1)..lastIndex)
            elements.add(this[i] to this[j])
    }

    return elements
}

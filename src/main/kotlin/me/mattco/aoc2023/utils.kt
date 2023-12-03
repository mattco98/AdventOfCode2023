package me.mattco.aoc2023

import kotlin.math.max
import kotlin.math.min

fun Int.rangeAround(n: Int, bounds: IntRange) =
    max(this - n, bounds.min())..min(this + n, bounds.max())

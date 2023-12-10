package me.mattco.aoc2023

class Day10 : Day() {
    private lateinit var startingPipe: Pipe

    private val input = inputLines.mapIndexed { row, line ->
        line.mapIndexedNotNull { column, char ->
            Pipe(char, column, row, mutableMapOf()).also {
                if (char == 'S') startingPipe = it
            }
        }
    }

    init {
        // Add connections for all pipes
        input.flatten().forEach(Pipe::addConnections)

        // Set the onMainLoop flag for all pipes on the main loop
        var pipe = startingPipe
        var nextPipe = startingPipe.connections.values.first() // starting direction doesn't matter

        do {
            nextPipe.onMainLoop = true
            nextPipe.next(nextPipe.directionOf(pipe)).let {
                pipe = nextPipe
                nextPipe = it
            }
        } while (pipe != startingPipe)

        // Clear connections for pipes that aren't on the main loop, which is important for part 2
        input.flatten().forEach {
            if (!it.onMainLoop)
                it.connections.clear()
        }
    }

    override fun part1() = input.flatten().count(Pipe::onMainLoop) / 2

    override fun part2(): Any {
        // Use the ray casting algorithm with horizontal scanline, but with one slight alteration:
        // Horizontally-connected pipes need special consideration. If both ends of the pipe have
        // the same vertical connections (e.g. F-----7), then the inside status shouldn't change.
        // If they have differing vertical connections (e.g. F----J), then the inside status should
        // toggle

        var numInside = 0

        for (row in input) {
            var inside = false
            var x = 0

            // The last pipe in this row will never be inside, since it can only be an edge or outside
            while (x != row.lastIndex) {
                val pipe = row[x]

                if (pipe.onMainLoop) {
                    inside = !inside
                } else if (inside) {
                    numInside++
                }

                // Handle horizontal pipes
                if (Direction.Right in pipe.connections) {
                    while (Direction.Right in row[x].connections)
                        x++

                    val rightEdge = row[x]

                    if ((Direction.Up in pipe.connections) != (Direction.Up in rightEdge.connections))
                        inside = !inside
                } else {
                    x++
                }
            }
        }

        return numInside
    }

    private inner class Pipe(val char: Char, val x: Int, val y: Int, val connections: MutableMap<Direction, Pipe>) {
        var onMainLoop = false

        fun next(previousDirection: Direction): Pipe {
            return connections.entries.first { it.key != previousDirection }.value
        }

        fun addConnections() {
            if (char == 'S') {
                input[y][x + 1]
                    .takeIf { it.char in setOf('-', 'J', '7') }
                    ?.let { connections[Direction.Right] = it }

                input[y + 1][x]
                    .takeIf { it.char in setOf('|', 'L', 'J') }
                    ?.let { connections[Direction.Down] = it }

                if (x > 0) {
                    input[y][x - 1]
                        .takeIf { it.char in setOf('-', 'L', 'F') }
                        ?.let { connections[Direction.Left] = it }
                }

                if (y > 0) {
                    input[y - 1][x]
                        .takeIf { it.char in setOf('|', '7', 'F') }
                        ?.let { connections[Direction.Up] = it }
                }

                return
            }

            directionMap[char]!!.forEach {
                val (newX, newY) = it.apply(x, y)
                if (newX in input[0].indices && newY in input.indices)
                    connections[it] = input[newY][newX]
            }
        }

        fun directionOf(pipe: Pipe) = connections.entries.first { it.value === pipe }.key

        override fun toString() = "Pipe('$char', ($x, $y))"
    }

    companion object {
        private val directionMap = mapOf(
            '-' to listOf(Direction.Left, Direction.Right),
            '|' to listOf(Direction.Up, Direction.Down),
            '7' to listOf(Direction.Left, Direction.Down),
            'F' to listOf(Direction.Right, Direction.Down),
            'L' to listOf(Direction.Up, Direction.Right),
            'J' to listOf(Direction.Up, Direction.Left),
            '.' to emptyList()
        )
    }
}

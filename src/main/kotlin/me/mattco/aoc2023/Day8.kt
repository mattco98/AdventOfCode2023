package me.mattco.aoc2023

class Day8 : Day() {
    private val input by lazy {
        val directions = inputLines[0].toCharArray().toList()
        val nodeIndices = inputLines.drop(2).withIndex().associate { (index, line) ->
            line.substringBefore(" = ") to index
        }
        Input(
            directions, 
            inputLines.drop(2).map {
                val (label, connections) = it.split(" = ")
                val (left, right) = connections.drop(1).dropLast(1).split(", ")
                Node(label, nodeIndices[left]!!, nodeIndices[right]!!)
            },
        )
    }

    override fun part1(): Any {
        var steps = 0
        var currentNode = input.nodes.first { it.name == "AAA" }

        while (currentNode.name != "ZZZ") {
            currentNode = currentNode.next(input.directions[steps % input.directions.size])
            steps++
        }

        return steps
    }

    override fun part2(): Any {
        val cycleLengths = input.nodes.filter { it.name.endsWith("A") }.map {
            var steps = 0
            var currentNode = it
    
            while (!currentNode.name.endsWith("Z")) {
                currentNode = currentNode.next(input.directions[steps % input.directions.size])
                steps++
            }
    
            steps.toLong()
        }

        return cycleLengths.reduce(::lcm)
    }

    data class Input(val directions: List<Char>, val nodes: List<Node>)

    inner class Node(val name: String, val left: Int, val right: Int) {
        fun next(dir: Char) = if (dir == 'L') input.nodes[left] else input.nodes[right]
    }
}

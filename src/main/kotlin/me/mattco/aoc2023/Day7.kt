package me.mattco.aoc2023

class Day7 : Day() {
    private val input = inputLines.map {
        val (cards, bid) = it.split(' ')
        Hand(cards.toCharArray().toList(), bid.toInt())
    }

    override fun part1() = solve(compareHands(::handTypePart1, ORDERING_PART_1))

    override fun part2() = solve(compareHands(::handTypePart2, ORDERING_PART_2))
    
    private fun solve(comparator: Comparator<Hand>) = input.sortedWith(comparator).withIndex().sumOf { 
        (it.index + 1) * it.value.bid 
    }

    private fun compareHands(handType: (Hand) -> Int, cardOrdering: List<Char>) = Comparator<Hand> { lhs, rhs ->
        val typeDiff = handType(lhs) - handType(rhs)
        if (typeDiff != 0)
            return@Comparator typeDiff
    
        for ((lhsCard, rhsCard) in lhs.cards.zip(rhs.cards)) {
            val cardDiff = compareCards(cardOrdering, lhsCard, rhsCard)
            if (cardDiff != 0)
                return@Comparator cardDiff
        }
    
        unreachable()
    }

    private fun handTypePart1(hand: Hand) = typeRankFromCounts(hand.cards.eachCount().values.toList())

    private fun handTypePart2(hand: Hand): Int {
        val numJokers = hand.cards.count { it == 'J' }
        if (numJokers == 5)
            return 6

        val counts = hand.cards.filter { it != 'J' }.eachCount().values.sortedDescending().toMutableList()
        counts[0] += numJokers
        return typeRankFromCounts(counts)
    }

    private fun compareCards(ordering: List<Char>, lhs: Char, rhs: Char) = ordering.indexOf(rhs) - ordering.indexOf(lhs)

    private fun typeRankFromCounts(counts: List<Int>) = when {
        counts.any { it == 5 } -> 6
        counts.any { it == 4 } -> 5
        counts.any { it == 3 } && counts.any { it == 2 } -> 4
        counts.any { it == 3 } -> 3
        counts.count { it == 2 } == 2 -> 2
        counts.count { it == 2 } == 1 -> 1
        counts.count { it > 1 } == 0 -> 0
        else -> unreachable()
    }

    data class Hand(val cards: List<Char>, val bid: Int) {
        override fun toString() = "Hand(${cards.joinToString("") { it.toString() }}, $bid)"
    }

    companion object {
        private val ORDERING_PART_1 = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        private val ORDERING_PART_2 = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    }
}

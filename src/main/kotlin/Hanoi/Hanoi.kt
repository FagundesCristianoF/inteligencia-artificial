package hanoi

import java.util.ArrayList

class Hanoi(private val discCount: Int = 3) {
    private val pegs: Array<Stack<Int>> = Array(PEG_COUNT) { Stack() }
    private var pegUsed: BooleanArray = BooleanArray(PEG_COUNT)

    init {
        configureInitialState()
    }

    private fun configureInitialState() {
        pegUsed = BooleanArray(PEG_COUNT)
        for (i in 0 until PEG_COUNT) {
            pegs[i] = Stack()
        }
        for (i in discCount downTo 1) {
            pegs[0].push(i)
        }
    }

    fun printHanoi() {
        for (i in 0 until PEG_COUNT) {
            print("Peg(${i + 1}): ")
            pegs[i].printStack()
            println()
        }
    }

    fun transition(): ArrayList<Hanoi> {
        val states = ArrayList<Hanoi>()
        for (i in 0 until PEG_COUNT) {
            if (pegs[i].discCount() > 0 && !pegUsed[i]) {
                for (j in 0 until PEG_COUNT) {
                    if (j != i) {
                        if (pegs[j].discCount() == 0) {
                            states.add(applyAction(i, j))
                        } else {
                            val topI = pegs[i].top()
                            val topJ = pegs[j].top()
                            if (topI != null && topJ != null && topI < topJ) {
                                states.add(applyAction(i, j))
                            }
                        }
                    }
                }
            }
        }
        return states
    }

    private fun applyAction(from: Int, to: Int): Hanoi {
        val newState = clone()
        val disc = newState.pegs[from].pop()
        if (disc != null) {
            newState.pegs[to].push(disc)
        }
        newState.pegUsed[to] = true
        newState.pegUsed[from] = false
        return newState
    }

    fun clone(): Hanoi {
        val clone = Hanoi(discCount)
        for (i in 0 until PEG_COUNT) {
            clone.pegs[i] = pegs[i].clone()
        }
        return clone
    }

    fun isGoal(): Boolean {
        for (i in 1 until PEG_COUNT) {
            return pegs[i].discCount() == discCount
        }
        return false
    }

    companion object {
        const val PEG_COUNT = 3
    }
}

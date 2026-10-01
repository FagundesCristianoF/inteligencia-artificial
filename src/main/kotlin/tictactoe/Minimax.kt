package tictactoe

class Minimax {
    private fun switchPlayer(current: String): String {
        return if (TicTacToe.PLAYER_O == current) {
            TicTacToe.PLAYER_X
        } else {
            TicTacToe.PLAYER_O
        }
    }

    fun bestMove(currentState: IntArray, player: String): Int {
        val node = Node(currentState)
        val minStates = node.transition(player)
        var max = Int.MIN_VALUE
        var bestState = currentState
        for (minState in minStates) {
            val score = evaluateMin(minState, switchPlayer(player))
            println("Value = $score")
            if (score > max) {
                max = score
                bestState = minState
            }
        }

        for (i in currentState.indices) {
            if (currentState[i] != bestState[i]) {
                return i
            }
        }

        return -1
    }

    private fun evaluateMin(minState: IntArray, player: String): Int {
        val node = Node(minState)
        val value = node.isTerminal()
        if (value != -2) {
            return value
        }

        val maxStates = node.transition(player)
        var min = Int.MAX_VALUE
        for (maxState in maxStates) {
            val score = evaluateMax(maxState, switchPlayer(player))
            if (score < min) {
                min = score
            }
        }
        return min
    }

    private fun evaluateMax(maxState: IntArray, player: String): Int {
        val node = Node(maxState)
        val value = node.isTerminal()
        if (value != -2) {
            return value
        }
        val minStates = node.transition(player)
        var max = Int.MIN_VALUE
        for (minState in minStates) {
            val score = evaluateMin(minState, switchPlayer(player))
            if (score > max) {
                max = score
            }
        }
        return max
    }
}

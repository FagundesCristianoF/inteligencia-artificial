package tictactoe

class Node(private val state: IntArray) {
    private var parent: Node? = null
    private val children: ArrayList<Node> = ArrayList()

    fun addChild(child: Node) {
        child.parent = this
        children.add(child)
    }

    fun isTerminal(): Int {
        var move = state[0]
        if (move == state[1] && move == state[2] && move != 0) {
            return move
        }
        if (move == state[3] && move == state[6] && move != 0) {
            return move
        }
        if (move == state[4] && move == state[8] && move != 0) {
            return move
        }
        move = state[3]
        if (move == state[4] && move == state[5] && move != 0) {
            return move
        }
        move = state[6]
        if (move == state[7] && move == state[8] && move != 0) {
            return move
        }
        move = state[6]
        if (move == state[4] && move == state[2] && move != 0) {
            return move
        }
        move = state[1]
        if (move == state[4] && move == state[7] && move != 0) {
            return move
        }
        move = state[2]
        if (move == state[5] && move == state[8] && move != 0) {
            return move
        }
        move = state[2]
        if (move == state[4] && move == state[6] && move != 0) {
            return move
        }
        for (i in state) {
            if (i == 0) {
                return -2
            }
        }
        return 0
    }

    fun getState(): IntArray {
        val newState = IntArray(9)
        for (i in state.indices) {
            newState[i] = state[i]
        }
        return newState
    }

    fun transition(player: String): ArrayList<IntArray> {
        val states = ArrayList<IntArray>()
        val playerId = when (player) {
            TicTacToe.PLAYER_X -> 1
            TicTacToe.PLAYER_O -> -1
            else -> 0
        }
        for (i in state.indices) {
            val newState = getState()
            if (state[i] == 0) {
                newState[i] = playerId
                states.add(newState)
            }
        }
        return states
    }
}

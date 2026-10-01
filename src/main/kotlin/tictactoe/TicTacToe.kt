package tictactoe

import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel

class TicTacToe : JPanel() {
    private val cells: ArrayList<JButton> = ArrayList()
    private var player: String = PLAYER_X
    private val minimax = Minimax()

    init {
        initBoard()
    }

    private fun initBoard() {
        layout = GridLayout(3, 3)

        for (i in 0 until 9) {
            val cell = JButton("")
            cells.add(cell)
            add(cell)
        }

        makeMove(minimax.bestMove(getState(), player), player)
        setEvents()
    }

    private fun setEvents() {
        for (cell in cells) {
            cell.addActionListener {
                cell.text = player
                switchPlayer()
                makeMove(minimax.bestMove(getState(), player), player)
            }
        }
    }

    fun makeMove(pos: Int, player: String) {
        cells[pos].text = player
        switchPlayer()
    }

    fun getState(): IntArray {
        val state = IntArray(9)
        for (i in cells.indices) {
            if (cells[i].text == PLAYER_X) {
                state[i] = 1
            }
            if (cells[i].text == PLAYER_O) {
                state[i] = -1
            }
        }
        return state
    }

    private fun switchPlayer() {
        player = if (player == PLAYER_X) {
            PLAYER_O
        } else {
            PLAYER_X
        }
    }

    companion object {
        const val PLAYER_X = "X"
        const val PLAYER_O = "O"
    }
}

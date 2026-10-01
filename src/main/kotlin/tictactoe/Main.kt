package tictactoe

import javax.swing.JFrame

fun main() {
    val window = JFrame("Tic-Tac-Toe")
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.setSize(500, 500)
    window.isVisible = true

    window.add(TicTacToe())
    window.validate()
}

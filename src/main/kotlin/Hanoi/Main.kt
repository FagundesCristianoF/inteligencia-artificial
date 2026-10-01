package hanoi

fun main() {
    val hanoi = Hanoi(5)
    hanoi.printHanoi()

    val root = Node(hanoi)
    var solution = root.breadthFirstSearch()
    val found = root.depthFirstSearch(root)
    if (found) {
        solution = root
    }

    var current: Node? = solution
    while (current != null) {
        current.printState()
        current = current.getChild(0)
    }
}

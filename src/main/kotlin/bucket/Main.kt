package bucket

fun depthFirstSearch(initialState: Node): Node? {
    if (initialState.isGoal()) {
        return initialState
    }
    val level = initialState.transition()
    for (node in level) {
        val solution = depthFirstSearch(node)
        if (solution != null) {
            return solution
        }
    }
    return null
}

fun breadthFirstSearch(initialState: Node): Node {
    var level = initialState.transition()
    while (true) {
        val aux = ArrayList<Node>()
        for (child in level) {
            if (child.isGoal()) {
                return child
            }
            aux.addAll(child.transition())
        }
        level = aux
    }
}

fun main() {
    val cap1 = 200
    val cap2 = 181
    val initialState = Node(Bucket(cap1, 0), Bucket(cap2, 0))
    var start = System.currentTimeMillis()
    var solution: Node? = breadthFirstSearch(initialState)
    var end = System.currentTimeMillis()
    println("Breadth-first solution found in t = ${end - start}")
    while (solution != null) {
        solution.printNode()
        solution = solution.getParent()
    }

    start = System.currentTimeMillis()
    solution = depthFirstSearch(Node(Bucket(cap1, 0), Bucket(cap2, 0)))
    end = System.currentTimeMillis()
    println("Depth-first solution found in t = ${end - start}")
    while (solution != null) {
        solution.printNode()
        solution = solution.getParent()
    }
}

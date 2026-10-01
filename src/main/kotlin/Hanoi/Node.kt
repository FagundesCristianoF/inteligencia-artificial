package hanoi

import java.util.ArrayList

class Node(private val state: Hanoi) {
    private val children: MutableList<Node> = ArrayList()
    private var parent: Node? = null
    private var level: Int = 0

    fun addChild(child: Node) {
        children.add(child)
        child.parent = this
        child.level = level + 1
    }

    fun getParent(): Node? {
        return parent
    }

    fun printState() {
        println("Level $level")
        state.printHanoi()
    }

    fun breadthFirstSearch(): Node {
        var levelNodes = ArrayList<Node>()
        var states = state.transition()
        for (nextState in states) {
            val child = Node(nextState)
            addChild(child)
        }
        levelNodes = ArrayList(children)
        while (true) {
            val nextLevel = ArrayList<Node>()
            for (child in levelNodes) {
                if (child.state.isGoal()) {
                    return child
                }
                states = child.state.transition()
                for (nextState in states) {
                    val newChild = Node(nextState)
                    child.addChild(newChild)
                    nextLevel.add(newChild)
                }
            }
            levelNodes = nextLevel
        }
    }

    fun depthFirstSearch(current: Node): Boolean {
        if (current.state.isGoal()) {
            return true
        }
        val states = current.state.transition()
        if (states.isEmpty()) {
            return false
        }
        for (nextState in states) {
            val child = Node(nextState)
            val foundGoal = depthFirstSearch(child)
            if (foundGoal) {
                current.addChild(child)
                return true
            }
        }
        return false
    }

    fun getChild(pos: Int): Node? {
        if (children.isEmpty()) {
            return null
        }
        return children[pos]
    }
}

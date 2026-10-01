package bucket

class Node(private val bucket1: Bucket, private val bucket2: Bucket) {
    private var parent: Node? = null
    private val children: MutableList<Node> = mutableListOf()
    private var level: Int = 0
    private var terminal: Boolean = false

    fun addChild(child: Node) {
        var aux: Node? = this
        while (aux != null) {
            if (aux.isEqual(child)) {
                child.terminal = true
            }
            aux = aux.parent
        }
        if (!child.terminal) {
            children.add(child)
            child.level = level + 1
            child.parent = this
        }
    }

    private fun isEqual(node: Node): Boolean {
        return node.bucket1.getAmount() == bucket1.getAmount() &&
            node.bucket2.getAmount() == bucket2.getAmount()
    }

    fun transition(): MutableList<Node> {
        if (terminal) {
            return mutableListOf()
        }
        if (bucket1.getAmount() == 0) {
            addChild(Node(bucket1.fill(), bucket2))
        } else {
            addChild(Node(bucket1.empty(), bucket2))
            val buckets = bucket1.transfer(bucket2)
            addChild(Node(buckets[0], buckets[1]))
        }
        if (bucket2.getAmount() == 0) {
            addChild(Node(bucket1, bucket2.fill()))
        } else {
            addChild(Node(bucket1, bucket2.empty()))
            val buckets = bucket2.transfer(bucket1)
            addChild(Node(buckets[1], buckets[0]))
        }
        return children
    }

    fun printNode() {
        println("${bucket1.getAmount()},${bucket2.getAmount()} ($level)")
    }

    fun getChildren(): MutableList<Node> {
        return children
    }

    fun getParent(): Node? {
        return parent
    }

    fun isGoal(): Boolean {
        return bucket1.getAmount() == 0 && bucket2.getAmount() == 35
    }
}

package hanoi

class Stack<T> {
    private val discs: ArrayList<T> = ArrayList()

    fun top(): T? {
        return if (discs.isNotEmpty()) {
            discs[discs.size - 1]
        } else {
            null
        }
    }

    fun push(disc: T) {
        discs.add(disc)
    }

    fun pop(): T? {
        return if (discs.isNotEmpty()) {
            discs.removeAt(discs.size - 1)
        } else {
            null
        }
    }

    fun printStack() {
        for (disc in discs) {
            print(disc.toString())
        }
    }

    fun clone(): Stack<T> {
        val clone = Stack<T>()
        for (disc in discs) {
            clone.discs.add(disc)
        }
        return clone
    }

    fun discCount(): Int {
        return discs.size
    }
}

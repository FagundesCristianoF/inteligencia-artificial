package knn

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.pow
import kotlin.math.sqrt

class KNN(dir: String, numObjs: Int, numAtr: Int) {
    private val data: Array<DoubleArray> = Array(numObjs) { DoubleArray(numAtr) }
    private var errors: Int = 0

    init {
        readFile(dir)
    }

    private fun readFile(dir: String) {
        try {
            BufferedReader(FileReader(File(dir))).use { reader ->
                var line = reader.readLine()
                var n = 0
                while (line != null) {
                    readAttributes(n, line)
                    line = reader.readLine()
                    n++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readAttributes(n: Int, line: String) {
        val attributes = line.split(",")
        for (i in attributes.indices) {
            data[n][i] = attributes[i].toDouble()
        }
    }

    fun printData() {
        for (i in data.indices) {
            for (j in data[i].indices) {
                print("${data[i][j]},")
            }
            println()
        }
    }

    fun classifyObject(objectStr: String): Int {
        val attributes = objectStr.split(",")
        val obj = DoubleArray(attributes.size - 1)
        for (i in 0 until attributes.size - 1) {
            obj[i] = attributes[i].toDouble()
        }
        val expected = attributes.last().toInt()

        var minDist = Double.MAX_VALUE
        var cls = -1
        for (i in data.indices) {
            val d = distance(data[i], obj)
            if (d < minDist) {
                minDist = d
                cls = data[i][data[i].size - 1].toInt()
            }
        }
        if (cls != expected) {
            errors++
        }
        return cls
    }

    fun distance(x: DoubleArray, o: DoubleArray): Double {
        var d = 0.0
        for (i in o.indices) {
            d += (x[i] - o[i]).pow(2)
        }
        return sqrt(d)
    }

    fun classifyFile(dir: String) {
        try {
            BufferedReader(FileReader(File(dir))).use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    val cls = classifyObject(line)
                    println("Class = $cls")
                    line = reader.readLine()
                }
                println("Errors = $errors")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val knn = KNN("/home/antonio/mnist.data", 10000, 785)
            // knn.printData()
            knn.classifyFile("/home/antonio/mnist.test")
        }
    }
}

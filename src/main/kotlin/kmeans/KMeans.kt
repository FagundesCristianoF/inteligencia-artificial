package kmeans

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.Arrays
import java.util.HashMap
import java.util.Random
import kotlin.math.sqrt

class KMeans(private val k: Int, dir: String) {
    private lateinit var data: Array<DoubleArray>
    private lateinit var centroids: Array<DoubleArray>

    init {
        readData(dir)
    }

    private fun readData(dir: String) {
        try {
            val file = File(dir)
            BufferedReader(FileReader(file)).use { input ->
                BufferedReader(FileReader(file)).use { countReader ->
                    val firstLine = input.readLine() ?: return
                    val total = countReader.lineSequence().count()
                    data = Array(total) { DoubleArray(firstLine.split(",").size) }

                    var line: String? = firstLine
                    var row = 0
                    while (line != null) {
                        parseData(line, row)
                        line = input.readLine()
                        row++
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cluster(outputPath: String) {
        generateCentroids()

        val groups = HashMap<Int, ArrayList<DoubleArray>>()
        for (i in 0 until k) {
            groups[i] = ArrayList()
        }

        val previousCentroids = Array(k) { DoubleArray(centroids[0].size) }
        var error = Double.MAX_VALUE
        val tolerance = 0.00001

        while (error > tolerance) {
            for (i in 0 until k) {
                groups[i]?.clear()
            }
            for (i in data.indices) {
                var minDist = Double.MAX_VALUE
                var group = -1
                for (j in 0 until k) {
                    val dist = euclideanDistance(data[i], centroids[j])
                    if (dist < minDist) {
                        group = j
                        minDist = dist
                    }
                }
                groups[group]?.add(data[i])
            }

            for (i in 0 until k) {
                val observations = groups[i] ?: ArrayList()
                val meanObs = DoubleArray(centroids[0].size)
                for (obs in observations) {
                    for (j in meanObs.indices) {
                        meanObs[j] += obs[j]
                    }
                }
                for (j in meanObs.indices) {
                    previousCentroids[i][j] = centroids[i][j]
                    centroids[i][j] = meanObs[j] / observations.size
                }
            }

            error = 0.0
            for (i in 0 until k) {
                error += euclideanDistance(previousCentroids[i], centroids[i])
            }
        }

        try {
            BufferedWriter(FileWriter(File(outputPath))).use { output ->
                for (i in 0 until k) {
                    val observations = groups[i] ?: ArrayList()
                    for (obs in observations) {
                        var line = ""
                        for (j in obs.indices) {
                            line += "${obs[j]},"
                        }
                        line += (i + 1).toString()
                        output.write("$line\n")
                    }
                    println(Arrays.toString(centroids[i]))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun euclideanDistance(p1: DoubleArray, p2: DoubleArray): Double {
        var distance = 0.0
        for (i in p1.indices) {
            distance += (p1[i] - p2[i]) * (p1[i] - p2[i])
        }
        return sqrt(distance)
    }

    private fun generateCentroids() {
        centroids = Array(k) { DoubleArray(data[0].size) }
        val pos = Random()
        for (i in 0 until k) {
            centroids[i] = data[pos.nextInt(data.size)]
        }
    }

    private fun parseData(line: String, row: Int) {
        val attributes = line.split(",")
        for (i in attributes.indices) {
            data[row][i] = attributes[i].toDouble()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val kmeans = KMeans(4, "/home/antonio/Desktop/data.csv")
            kmeans.cluster("/home/antonio/Desktop/groups.csv")
        }
    }
}

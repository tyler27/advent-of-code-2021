package com.tyler.puzzle.day16

import com.tyler.puzzle.Puzzle

/**
 * @author Tyler Telis
 * @email <xlitersps@gmail.com>
 */
class Day16 : Puzzle<Any> {
    private val inputs = Day16::class.java.getResourceAsStream(INPUT_FILE)!!
        .bufferedReader()
        .readLines()

    var totalVersion = 0
    var totalEvaluatedResult = 0L

    fun String.binaryToDigit() = this.toInt(2)
    fun String.binaryToLong() = this.toLong(2)

    private fun String.getNBit(startIndex: Int, numberOfBit: Int) =
        this.slice(startIndex until startIndex + numberOfBit).binaryToDigit()

    val performCalculation: (typeId: Int, list: List<Long>) -> Long = { typeId, list ->
        when (typeId) {
            0 -> list.sum()
            1 -> list.fold(1L) { acc, i -> acc * i }
            2 -> list.minOrNull()!!
            3 -> list.maxOrNull()!!
            5 -> {
                require(list.size == 2) { "Should be two sub-packets for typeId - $typeId" }
                if (list[0] > list[1]) 1L else 0L
            }
            6 -> {
                require(list.size == 2) { "Should be two sub-packets for typeId - $typeId" }
                if (list[0] < list[1]) 1L else 0L
            }
            7 -> {
                require(list.size == 2) { "Should be two sub-packets for typeId - $typeId" }
                if (list[0] == list[1]) 1L else 0L
            }
            else -> throw IllegalStateException("Invalid typeId")
        }.also { totalEvaluatedResult = it }
    }

    private fun parsePackets(packet: String, literalValues: MutableList<Long>): Pair<Int, Long> {
        var currentIndex = 0
        val getNBit: (startIndex: Int, numberOfBit: Int) -> Int =
            { s, n -> packet.getNBit(s, n).also { currentIndex += n } }

        totalVersion += getNBit(0, 3) // version

        when (val typeId = getNBit(3, 3)) { // typeId
            4 -> {
                val stringBuilder = StringBuilder()
                while (packet[currentIndex] != '0') {
                    stringBuilder.append(packet.substring(currentIndex + 1..currentIndex + 4))
                    getNBit(currentIndex, 5)
                }
                stringBuilder.append(packet.substring(currentIndex + 1..currentIndex + 4))
                literalValues.add(stringBuilder.toString().binaryToLong())
                getNBit(currentIndex, 5)
                return Pair(currentIndex, -1L)
            }
            else -> {
                when (packet[currentIndex++]) {
                    '0' -> {
                        var index = currentIndex
                        val totalLengthInBits = getNBit(currentIndex, 15).also { index += 15 }
                        var subPacketLength = 0
                        val subPacketLiteralValue = mutableListOf<Long>()
                        val resultList = mutableListOf<Long>()
                        while (true) {
                            val subPacketParsedResult = parsePackets(packet.substring(currentIndex + subPacketLength, packet.length), literalValues = subPacketLiteralValue)
                            subPacketLength += subPacketParsedResult.first
                            if (subPacketParsedResult.second != -1L) resultList.add(subPacketParsedResult.second)
                            if (subPacketLength == totalLengthInBits) break
                        }

                        return if (resultList.isNotEmpty() && subPacketLiteralValue.isNotEmpty()) {
                            val newList = mutableListOf<Long>()
                            newList.add(performCalculation(typeId, resultList))
                            newList.add(performCalculation(typeId, subPacketLiteralValue))
                            Pair(index + subPacketLength, performCalculation(typeId, newList))
                        } else if (subPacketLiteralValue.isNotEmpty()) {
                            Pair(index + subPacketLength, performCalculation(typeId, subPacketLiteralValue))
                        } else if (resultList.isNotEmpty()) {
                            Pair(index + subPacketLength, performCalculation(typeId, resultList))
                        } else {
                            Pair(index + subPacketLength, -1L)
                        }
                    }
                    '1' -> {
                        var indexToReturn = currentIndex
                        val next11Bits = getNBit(currentIndex, 11).also { indexToReturn += 11 }
                        var counter = 0
                        var index = 0
                        val subPacketLiteralValue = mutableListOf<Long>()
                        val resultList = mutableListOf<Long>()
                        while (next11Bits != counter++) {
                            val subPacketParsedResult = parsePackets(packet.substring(currentIndex + index, packet.length), literalValues = subPacketLiteralValue)
                            index += subPacketParsedResult.first
                            if (subPacketParsedResult.second != -1L) resultList.add(subPacketParsedResult.second)
                        }

                        return if (resultList.isNotEmpty() && subPacketLiteralValue.isNotEmpty()) {
                            val newList = mutableListOf<Long>()
                            newList.add(performCalculation(typeId, resultList))
                            newList.add(performCalculation(typeId, subPacketLiteralValue))
                            Pair(indexToReturn + index, performCalculation(typeId, newList))
                        } else if (subPacketLiteralValue.isNotEmpty()) {
                            Pair(indexToReturn + index, performCalculation(typeId, subPacketLiteralValue))
                        } else if (resultList.isNotEmpty()) {
                            Pair(indexToReturn + index, performCalculation(typeId, resultList))
                        } else {
                            Pair(indexToReturn + index, -1L)
                        }
                    }
                }
            }
        }

        return Pair(currentIndex, -1L)
    }


    private companion object {
        const val INPUT_FILE = "/day16/input.txt"
    }

    override fun solvePartOne(): Int {
        totalVersion = 0
        val bitsTransmissionInBinary = inputs[0].map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
        parsePackets(packet = bitsTransmissionInBinary, mutableListOf())
        return totalVersion
    }

    override fun solvePartTwo(): Long {
        totalVersion = 0
        val bitsTransmissionInBinary = inputs[0].map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
        parsePackets(bitsTransmissionInBinary, mutableListOf())
        return totalEvaluatedResult
    }
}

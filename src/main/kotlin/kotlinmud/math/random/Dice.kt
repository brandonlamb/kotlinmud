package kotlinmud.math.random

import kotlin.random.Random

fun d20(): Int {
    return Random.nextInt(1, 20)
}

fun dN(rolls: Int, number: Int): Int {
    return Random.nextInt(1, Math.max(2, number)) + if (rolls > 0) dN(rolls - 1, Math.max(number, rolls)) else 0
}

fun percentRoll(): Int {
    return Random.nextInt(1, 100)
}

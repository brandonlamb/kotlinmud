package kotlinmud.fs

fun str(value: String): String {
    return "$value~"
}

fun int(value: Int): String {
    return "#$value"
}

fun end(): String {
    return str("\nend")
}

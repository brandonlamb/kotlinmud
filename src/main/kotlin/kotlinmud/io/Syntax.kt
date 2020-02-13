package kotlinmud.io

enum class Syntax(val syntax: String) {
    COMMAND("command"),
    DIRECTION_TO_EXIT("direction to exit"),
    ITEM_IN_ROOM("item in room"),
    ITEM_IN_INVENTORY("item in inventory"),
    NOOP("noop"),
}

package kotlinmud.action.contextBuilder

import kotlinmud.action.Context
import kotlinmud.action.Status
import kotlinmud.io.Syntax
import kotlinmud.room.Room

class ItemInRoomContextBuilder(private val room: Room) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        return room.inventory.items.find { kotlinmud.string.matches(it.name, word) }
                ?.let { Context<Any>(syntax, Status.OK, it) }
            ?: Context<Any>(syntax, Status.FAILED, "you don't see that anywhere.")
    }
}

package kotlinmud.action.contextBuilder

import kotlinmud.action.model.Context
import kotlinmud.action.type.Status
import kotlinmud.io.type.Syntax
import kotlinmud.item.model.Item
import kotlinmud.item.service.ItemService
import kotlinmud.mob.model.Mob
import kotlinmud.room.model.Room

class AvailableItemInventoryContextBuilder(private val mob: Mob, private val room: Room, private val itemService: ItemService) : ContextBuilder {
    companion object {
        fun isMatch(item: Item, word: String): Boolean {
            return item.hasInventory && kotlinmud.helper.string.matches(item.name, word)
        }
    }

    override fun build(syntax: Syntax, word: String): Context<Any> {
        return itemService.findAllByOwner(mob).find {
            isMatch(it, word)
        }?.let {
            Context<Any>(syntax, Status.OK, it)
        } ?: itemService.findAllByOwner(room).find {
            isMatch(it, word)
        }?.let {
            Context<Any>(syntax, Status.OK, it)
        } ?: Context<Any>(
            syntax,
            Status.ERROR,
            "you don't see anywhere to put that."
        )
    }
}

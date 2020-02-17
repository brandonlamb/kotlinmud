package kotlinmud.action.contextBuilder

import kotlinmud.action.Context
import kotlinmud.action.Status
import kotlinmud.io.Syntax
import kotlinmud.mob.MobEntity
import kotlinmud.room.RoomEntity
import kotlinmud.service.MobService
import kotlinmud.string.matches
import org.jetbrains.exposed.sql.transactions.transaction

class AvailableNounContextBuilder(
    private val mobService: MobService,
    private val mob: MobEntity,
    private val room: RoomEntity) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        val target = transaction {
            mobService.getMobsForRoom(room).find {
                matches(it.name, word)
            }?.name ?: mob.inventory.items.find {
                matches(it.name, word)
            }?.name ?: room.inventory.items.find {
                matches(it.name, word)
            }?.name
        } ?: return Context(syntax, Status.FAILED, "you don't see anything like that here.")
        return Context(syntax, Status.OK, "$target is here.")
    }
}
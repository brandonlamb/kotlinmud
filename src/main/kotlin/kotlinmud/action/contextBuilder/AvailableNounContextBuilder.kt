package kotlinmud.action.contextBuilder

import kotlinmud.action.model.Context
import kotlinmud.action.type.Status
import kotlinmud.affect.type.AffectType
import kotlinmud.helper.string.matches
import kotlinmud.io.type.Syntax
import kotlinmud.item.service.ItemService
import kotlinmud.mob.model.Mob
import kotlinmud.mob.service.MobService
import kotlinmud.room.model.Room

class AvailableNounContextBuilder(
    private val mobService: MobService,
    private val itemService: ItemService,
    private val mob: Mob,
    private val room: Room
) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        val target = mobService.getMobsForRoom(room).find {
                matches(it.name, word) && it.affects().findByType(AffectType.INVISIBILITY) == null
            } ?: itemService.findByOwner(mob, word)
            ?: itemService.findByOwner(room, word)
            ?: return Context(
            syntax,
            Status.FAILED,
            "you don't see anything like that here."
        )
        return Context(syntax, Status.OK, target)
    }
}

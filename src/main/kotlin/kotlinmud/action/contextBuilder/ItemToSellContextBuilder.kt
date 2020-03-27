package kotlinmud.action.contextBuilder

import kotlinmud.action.Context
import kotlinmud.action.Status
import kotlinmud.io.Syntax
import kotlinmud.mob.JobType
import kotlinmud.mob.Mob

class ItemToSellContextBuilder(private val mob: Mob, private val mobsInRoom: List<Mob>) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        val shopkeeper = mobsInRoom.find { it.job == JobType.SHOPKEEPER }
            ?: return Context(Syntax.ITEM_TO_SELL, Status.ERROR, "no merchant is here.")
        val item = mob.inventory.items.find { kotlinmud.string.matches(it.name, word) }
            ?: return Context(Syntax.ITEM_TO_SELL, Status.ERROR, "you don't have anything like that.")
        if (item.worth > shopkeeper.gold) {
            return Context(Syntax.ITEM_TO_SELL, Status.ERROR, "they can't afford that.")
        }
        return Context(Syntax.ITEM_TO_SELL, Status.OK, item)
    }
}

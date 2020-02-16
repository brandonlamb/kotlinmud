package kotlinmud.action.contextBuilder

import kotlinmud.action.Context
import kotlinmud.action.Status
import kotlinmud.io.Syntax
import kotlinmud.mob.MobEntity
import kotlinmud.string.matches

class MobInRoomContextBuilder(private val mobs: List<MobEntity>) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        return mobs.find { matches(it.name, word) }?.let { Context<Any>(syntax, Status.OK, it) }
            ?: Context<Any>(syntax, Status.FAILED, "you don't see them anywhere.")
    }
}
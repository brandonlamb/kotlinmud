package kotlinmud.action.contextBuilder

import kotlinmud.action.model.Context
import kotlinmud.action.type.Status
import kotlinmud.io.type.Syntax
import kotlinmud.mob.model.Mob
import kotlinmud.mob.type.JobType

class SpellFromHealerContextBuilder(private val mobs: List<Mob>) : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        val mob = mobs.find {
            it.job == JobType.HEALER
        } ?: return Context<Any>(
            syntax,
            Status.ERROR,
            "you don't see any healers here."
        )
        return mob.skills.keys.find {
            kotlinmud.helper.string.matches(it.toString(), word)
        }?.let {
            Context<Any>(syntax, Status.OK, it)
        } ?: Context<Any>(syntax, Status.ERROR, "they don't know that.")
    }
}

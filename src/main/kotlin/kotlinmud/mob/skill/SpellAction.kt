package kotlinmud.mob.skill

import kotlinmud.io.Request

interface SpellAction : SkillAction {
    override fun matchesRequest(request: Request): Boolean {
        return request.args.size > 1 && kotlinmud.string.matches(type.toString(), request.getSubject())
    }
}
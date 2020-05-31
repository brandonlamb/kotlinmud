package kotlinmud.affect

import kotlinmud.Noun
import kotlinmud.affect.model.AffectInstance
import kotlinmud.affect.type.AffectType
import kotlinmud.io.Message
import kotlinmud.mob.model.Mob

interface Affect {
    val type: AffectType
    fun messageFromInstantiation(mob: Mob, target: Noun?): Message
    fun messageFromWearOff(target: Noun): Message
    fun createInstance(timeout: Int): AffectInstance
}

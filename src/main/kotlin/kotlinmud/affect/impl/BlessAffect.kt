package kotlinmud.affect.impl

import kotlinmud.Noun
import kotlinmud.affect.Affect
import kotlinmud.affect.AffectInstance
import kotlinmud.affect.AffectType
import kotlinmud.io.Message
import kotlinmud.io.MessageBuilder
import kotlinmud.mob.Mob

class BlessAffect : Affect {
    override val type: AffectType = AffectType.BLESS

    override fun messageFromInstantiation(mob: Mob, target: Noun?): Message {
        return MessageBuilder()
            .toActionCreator("You feel blessed.")
            .toObservers("$mob is blessed.")
            .build()
    }

    override fun messageFromWearOff(target: Noun): Message {
        return MessageBuilder()
            .toActionCreator("You no longer feel blessed.")
            .toObservers("$target no longer is blessed.")
            .build()
    }

    override fun createInstance(timeout: Int): AffectInstance {
        return AffectInstance(type, timeout)
    }
}

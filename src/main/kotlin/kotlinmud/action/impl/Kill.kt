package kotlinmud.action.impl

import kotlinmud.action.model.Action
import kotlinmud.action.mustBeStanding
import kotlinmud.action.type.Command
import kotlinmud.io.MessageBuilder
import kotlinmud.io.Syntax
import kotlinmud.io.mobInRoom
import kotlinmud.mob.model.Mob

fun createKillAction(): Action {
    return Action(Command.KILL, mustBeStanding(), mobInRoom()) {
        val target = it.get<Mob>(Syntax.MOB_IN_ROOM)
        it.createFight()
        it.createOkResponse(
            MessageBuilder()
                .toActionCreator("you scream and attack $target!")
                .toTarget("${it.getMob()} screams and attacks you!")
                .toObservers("${it.getMob()} screams and attacks $target!")
                .build()
        )
    }
}

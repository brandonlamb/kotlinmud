package kotlinmud.action.impl.room.owner

import kotlinmud.action.helper.mustBeAlert
import kotlinmud.action.model.Action
import kotlinmud.action.type.Command
import kotlinmud.io.factory.subcommandPlayerMob
import kotlinmud.io.model.MessageBuilder
import kotlinmud.io.type.Syntax
import kotlinmud.mob.model.Mob

fun createOwnerSetAction(): Action {
    return Action(Command.OWNER_SET, mustBeAlert(), subcommandPlayerMob()) {
        val mob = it.get<Mob>(Syntax.PLAYER_MOB)
        it.getRoom().owner = mob
        it.createOkResponse(
            MessageBuilder()
                .toActionCreator("you have made $mob the new owner of this room.")
                .toObservers("${it.getMob()} has made $mob the new owner of this room.")
                .build()
        )
    }
}

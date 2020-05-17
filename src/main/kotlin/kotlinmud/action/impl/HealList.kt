package kotlinmud.action.impl

import kotlinmud.action.Action
import kotlinmud.action.Command
import kotlinmud.action.mustBeStanding
import kotlinmud.io.command
import kotlinmud.io.messageToActionCreator

fun createHealListAction(): Action {
    return Action(Command.HEAL, mustBeStanding(), command()) {
        it.createResponse(messageToActionCreator("success, healer"))
    }
}

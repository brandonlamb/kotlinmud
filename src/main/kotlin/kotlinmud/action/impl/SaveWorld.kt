package kotlinmud.action.impl

import kotlinmud.action.Action
import kotlinmud.action.ActionContextService
import kotlinmud.action.Command
import kotlinmud.action.mustBeAlert
import kotlinmud.fs.saver.WorldSaver
import kotlinmud.io.Message
import kotlinmud.io.Request
import kotlinmud.io.Syntax

fun createSaveWorldAction(worldSaver: WorldSaver): Action {
    return Action(
        Command.SAVE_WORLD,
        mustBeAlert(),
        listOf(Syntax.COMMAND),
        { svc: ActionContextService, _: Request ->
            worldSaver.save()
            svc.createResponse(Message("world saved"))
        }
    )
}
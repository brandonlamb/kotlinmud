package kotlinmud.action.impl

import kotlinmud.action.Action
import kotlinmud.action.ActionContextService
import kotlinmud.action.Command
import kotlinmud.action.mustBeFighting
import kotlinmud.io.Message
import kotlinmud.io.Request
import kotlinmud.io.Syntax
import kotlinmud.mob.skill.Cost
import kotlinmud.mob.skill.CostType

fun createFleeAction(): Action {
    return Action(
        Command.FLEE,
        mustBeFighting(),
        listOf(Syntax.COMMAND),
        { svc: ActionContextService, request: Request ->
            svc.endFightFor(request.mob)
            val exit = request.room.exits.random()
            svc.moveMob(
                request.mob,
                exit.destination,
                exit.direction)
            svc.createResponse(
                Message(
                    "you flee!",
                    "${request.mob.name} flees!")
            )
        },
        listOf(
            Cost(CostType.MV_PERCENT, 25)
        ),
        Command.LOOK
    )
}

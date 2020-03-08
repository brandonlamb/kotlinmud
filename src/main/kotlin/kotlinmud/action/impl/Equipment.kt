package kotlinmud.action.impl

import kotlinmud.action.*
import kotlinmud.io.Message
import kotlinmud.io.Request
import kotlinmud.io.Syntax
import kotlinmud.io.createResponseWithEmptyActionContext
import kotlinmud.item.Item

fun createEquipmentAction(): Action {
    return Action(
        Command.EQUIPMENT,
        mustBeAlert(),
        listOf(Syntax.COMMAND),
        { _: ActionContextService, request: Request ->
            val items = request.mob.equipped.items.toList()
            createResponseWithEmptyActionContext(
                Message("Your equipment:\n\n${describeEquipment(items)}")
            )
        })
}

fun describeEquipment(items: List<Item>): String {
    return items.joinToString("\n") { "${it.position.toString().toLowerCase()} - $it" }
}

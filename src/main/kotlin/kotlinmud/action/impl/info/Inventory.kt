package kotlinmud.action.impl.info

import kotlinmud.action.Action
import kotlinmud.action.Command
import kotlinmud.action.mustBeAwake
import kotlinmud.io.createResponseWithEmptyActionContext
import kotlinmud.io.messageToActionCreator
import kotlinmud.item.Item

fun createInventoryAction(): Action {
    return Action(Command.INVENTORY, mustBeAwake()) {
        val items = it.getItemsFor(it.getMob())
        createResponseWithEmptyActionContext(
            messageToActionCreator("Your inventory:\n\n${describeItems(items)}")
        )
    }
}

fun describeItems(items: List<Item>): String {
    return items.groupBy { it.id }
        .map { if (it.value.size > 1) "(${it.value.size}) " else "" + it.value[0].name }
        .fold("\n") { acc, it -> "$acc$it\n" }
}

package kotlinmud.action.impl

import kotlinmud.action.Action
import kotlinmud.action.ActionContextService
import kotlinmud.action.Command
import kotlinmud.action.mustBeAlert
import kotlinmud.io.Message
import kotlinmud.io.Request
import kotlinmud.io.Syntax
import kotlinmud.item.Item
import kotlinmud.mob.JobType

fun createSellAction(): Action {
    return Action(
        Command.SELL,
        mustBeAlert(),
        listOf(Syntax.COMMAND, Syntax.ITEM_IN_INVENTORY),
        { svc: ActionContextService, request: Request ->
            val item: Item = svc.get(Syntax.ITEM_IN_INVENTORY)
            val shopkeeper = svc.getMobsInRoom(request.room).find { it.job == JobType.SHOPKEEPER }!!
            shopkeeper.inventory.items.add(item)
            request.mob.inventory.items.remove(item)
            request.mob.gold += item.value
            shopkeeper.gold -= item.value
            svc.createResponse(
                Message(
                    "you buy $item from $shopkeeper for ${item.value}.",
                    "${request.mob} buys $item from you.",
                    "${request.mob} buys $item from $shopkeeper."
                )
            )
        }
    )
}
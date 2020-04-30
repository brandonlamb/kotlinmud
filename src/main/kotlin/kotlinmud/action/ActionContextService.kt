package kotlinmud.action

import kotlinmud.event.Event
import kotlinmud.event.EventType
import kotlinmud.event.event.FightStartedEvent
import kotlinmud.event.event.SocialEvent
import kotlinmud.io.IOStatus
import kotlinmud.io.Message
import kotlinmud.io.NIOClients
import kotlinmud.io.NIOServer
import kotlinmud.io.Response
import kotlinmud.io.Syntax
import kotlinmud.item.HasInventory
import kotlinmud.item.Item
import kotlinmud.mob.Mob
import kotlinmud.mob.fight.Fight
import kotlinmud.service.EventService
import kotlinmud.service.ItemService
import kotlinmud.service.MobService
import kotlinmud.social.Social
import kotlinmud.world.room.Direction
import kotlinmud.world.room.Room
import kotlinmud.world.room.RoomBuilder

class ActionContextService(
    private val mobService: MobService,
    private val itemService: ItemService,
    private val eventService: EventService,
    private val actionContextList: ActionContextList,
    private val server: NIOServer
) {
    fun getRecall(): Room {
        return mobService.getStartRoom()
    }

    fun <T> get(syntax: Syntax): T {
        return actionContextList.getResultBySyntax(syntax)
    }

    fun getMobsInRoom(room: Room): List<Mob> {
        return mobService.getMobsForRoom(room)
    }

    fun moveMob(mob: Mob, room: Room, direction: Direction) {
        mobService.moveMob(mob, room, direction)
    }

    fun putMobInRoom(mob: Mob, room: Room) {
        mobService.putMobInRoom(mob, room)
    }

    fun createResponse(message: Message, status: IOStatus = IOStatus.OK): Response {
        return Response(status, actionContextList, message)
    }

    fun createFightFor(mob: Mob) {
        val target: Mob = get(Syntax.MOB_IN_ROOM)
        val fight = Fight(mob, target)
        mobService.addFight(fight)
        eventService.publish(Event(EventType.FIGHT_STARTED, FightStartedEvent(fight, mob, target)))
    }

    fun endFightFor(mob: Mob) {
        mobService.endFightFor(mob)
    }

    fun publishSocial(social: Social) {
        eventService.publish(Event(EventType.SOCIAL, SocialEvent(social)))
    }

    fun getClients(): NIOClients {
        return server.getClients()
    }

    fun getItemsFor(hasInventory: HasInventory): List<Item> {
        return itemService.findAllByOwner(hasInventory)
    }

    fun getItemGroupsFor(mob: Mob): Map<Int, List<Item>> {
        return itemService.getItemGroups(mob)
    }

    fun changeItemOwner(item: Item, hasInventory: HasInventory) {
        itemService.changeItemOwner(item, hasInventory)
    }

    fun destroy(item: Item) {
        itemService.destroy(item)
    }

    fun createRoomBuilder(mob: Mob, srcRoom: Room, name: String, direction: Direction): RoomBuilder {
        val roomBuilder = mobService.createRoomBuilder(mob, srcRoom, direction)
        roomBuilder.name(name)
        return roomBuilder
    }

    fun buildRoom(mob: Mob): Room {
        return mobService.buildRoom(mob)
    }
}

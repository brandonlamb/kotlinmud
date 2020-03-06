package kotlinmud.action

import kotlinmud.event.Event
import kotlinmud.event.EventResponse
import kotlinmud.event.EventType
import kotlinmud.event.event.SocialEvent
import kotlinmud.io.*
import kotlinmud.mob.Mob
import kotlinmud.mob.fight.Fight
import kotlinmud.room.Direction
import kotlinmud.room.Room
import kotlinmud.service.EventService
import kotlinmud.service.MobService

class ActionContextService(
    private val mobService: MobService,
    private val eventService: EventService,
    private val actionContextList: ActionContextList
) {

    fun <T> get(syntax: Syntax): T {
        return actionContextList.getResultBySyntax(syntax)
    }

    fun getMobsInRoom(room: Room): List<Mob> {
        return mobService.getMobsForRoom(room)
    }

    fun moveMob(mob: Mob, room: Room, direction: Direction) {
        mobService.moveMob(mob, room, direction)
    }

    fun sendMessageToRoom(message: Message, room: Room, actionCreator: Mob, target: Mob? = null) {
        mobService.sendMessageToRoom(message, room, actionCreator, target)
    }

    fun createResponse(message: Message, status: IOStatus = IOStatus.OK): Response {
        return Response(status, actionContextList, message)
    }

    fun createFightFor(mob: Mob) {
        mobService.addFight(Fight(mob, get(Syntax.MOB_IN_ROOM)))
    }

    fun endFightFor(mob: Mob) {
        mobService.endFightFor(mob)
    }

    fun publishSocial(social: Social) {
        eventService.publish<SocialEvent, EventResponse<SocialEvent>>(
            Event(EventType.SOCIAL, SocialEvent(social)))
    }
}

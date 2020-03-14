package kotlinmud.test

import kotlinmud.attributes.Attribute
import kotlinmud.event.Event
import kotlinmud.event.EventResponse
import kotlinmud.io.IOStatus
import kotlinmud.io.Request
import kotlinmud.io.Response
import kotlinmud.item.Inventory
import kotlinmud.item.Item
import kotlinmud.item.Position
import kotlinmud.mob.JobType
import kotlinmud.mob.Mob
import kotlinmud.mob.MobRoom
import kotlinmud.mob.fight.Fight
import kotlinmud.mob.fight.Round
import kotlinmud.room.Room
import kotlinmud.service.*

class TestService(
    private val fixtureService: FixtureService,
    private val mobService: MobService,
    private val actionService: ActionService,
    private val respawnService: RespawnService,
    private val eventService: EventService
) {
    init {
        fixtureService.createItem(mobService.getStartRoom().inventory)
    }

    fun <T, A> publish(event: Event<T>): EventResponse<A> {
        return eventService.publish(event)
    }

    fun respawnWorld() {
        respawnService.respawn()
    }

    fun getStartRoom(): Room {
        return mobService.getStartRoom()
    }

    fun createMob(job: JobType = JobType.NONE): Mob {
        val mob = fixtureService.createMobBuilder()
            .setJob(job)
            .equip(buildItem(itemBuilder()
                .setPosition(Position.WEAPON)
                .setAttribute(Attribute.HIT, 5)
                .setAttribute(Attribute.DAM, 5)))
            .build()
        mobService.addMob(mob)
        return mob
    }

    fun mobBuilder(): Mob.Builder {
        return fixtureService.createMobBuilder()
    }

    fun buildMob(mobBuilder: Mob.Builder): Mob {
        val mob = mobBuilder.build()
        mobService.addMob(mob)
        return mob
    }

    fun createItem(inventory: Inventory): Item {
        return fixtureService.createItem(inventory)
    }

    fun itemBuilder(): Item.Builder {
        return fixtureService.createItemBuilder()
    }

    fun buildItem(itemBuilder: Item.Builder): Item {
        // item service?
        return itemBuilder.build()
    }

    fun getMobRooms(): List<MobRoom> {
        return mobService.getMobRooms()
    }

    fun getRoomForMob(mob: Mob): Room {
        return mobService.getRoomForMob(mob)
    }

    fun getMobsForRoom(room: Room): List<Mob> {
        return mobService.getMobsForRoom(room)
    }

    fun pruneDeadMobs() {
        mobService.pruneDeadMobs()
    }

    fun decrementDelays() {
        mobService.decrementDelays()
    }

    fun decrementAffects() {
        mobService.decrementAffects()
    }

    fun runAction(mob: Mob, input: String): Response {
        return actionService.run(Request(mob, input, mobService.getRoomForMob(mob)))
    }

    fun runActionForIOStatus(mob: Mob, input: String, status: IOStatus): Response {
        var i = 0
        while (i < 100) {
            val response = runAction(mob, input)
            if (response.status == status) {
                return response
            }
            i++
        }
        throw Exception("cannot generate desired IOStatus")
    }

    fun addFight(fight: Fight) {
        mobService.addFight(fight)
    }

    fun findFightForMob(mob: Mob): Fight? {
        return mobService.findFightForMob(mob)
    }

    fun proceedFights(): List<Round> {
        return mobService.proceedFights()
    }
}

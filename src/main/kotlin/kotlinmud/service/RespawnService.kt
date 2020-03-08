package kotlinmud.service

import kotlin.system.measureTimeMillis
import kotlinmud.loader.World
import kotlinmud.loader.model.reset.ItemMobReset
import kotlinmud.loader.model.reset.MobReset
import kotlinmud.mob.Mob
import kotlinmud.room.Room

class RespawnService(
    private val world: World,
    private val mobService: MobService,
    private val itemService: ItemService
) {

    fun respawn() {
        val time = measureTimeMillis {
            world.mobResets.toList().forEach { reset ->
                val room = world.rooms.get(reset.roomId)
                while (mobCanRespawn(reset, room)) {
                    val mob = world.mobs.get(reset.mobId)
                    addItemsToMob(mob)
                    mobService.putMobInRoom(mob.copy(), room)
                }
            }
        }
        println("respawn took $time ms")
    }

    private fun addItemsToMob(mob: Mob) {
        filterItemMobResets(mob.id).forEach { itemReset ->
            val item = world.items.get(itemReset.itemId)
            while (itemMobCanRespawn(itemReset, mob)) {
                with(item.copy(), {
                    itemService.add(this)
                    mob.inventory.items.add(this)
                })
            }
        }
    }

    private fun filterItemMobResets(mobId: Int): List<ItemMobReset> {
        return world.itemMobResets.toList().filter { it.mobId == mobId }
    }

    private fun itemMobCanRespawn(reset: ItemMobReset, mob: Mob): Boolean {
        return reset.maxInInventory > mob.inventory.items.filter { it.id == reset.itemId }.size &&
                reset.maxInWorld > itemService.getItemsById(reset.itemId).size
    }

    private fun mobCanRespawn(reset: MobReset, room: Room): Boolean {
        return reset.maxInRoom > mobService.getMobsForRoom(room).filter { mob -> mob.id == reset.mobId }.size &&
                reset.maxInWorld > mobService.getMobRooms().filter { mobRoom -> mobRoom.mob.id == reset.mobId }.size
    }
}
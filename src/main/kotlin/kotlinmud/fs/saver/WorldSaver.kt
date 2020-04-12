package kotlinmud.fs.saver

import java.io.File
import kotlinmud.fs.saver.mapper.mapDoor
import kotlinmud.fs.saver.mapper.mapItem
import kotlinmud.fs.saver.mapper.mapMob
import kotlinmud.fs.saver.mapper.mapRoom
import kotlinmud.fs.saver.mapper.reset.mapItemMobReset
import kotlinmud.fs.saver.mapper.reset.mapItemRoomReset
import kotlinmud.fs.saver.mapper.reset.mapMobReset
import kotlinmud.world.World

const val BASE_DIR = "state/bootstrap_world"

class WorldSaver(private val world: World) {
    fun save() {
        checkDirectoryExistence()
        saveMobs()
        saveRooms()
        saveDoors()
        saveItems()
        saveMobResets()
        saveItemMobResets()
        saveItemRoomResets()
    }

    private fun checkDirectoryExistence() {
        val base = File("$BASE_DIR/reset")
        if (!base.exists()) {
            base.mkdirs()
        }
    }

    private fun saveMobs() {
        val file = File("state/bootstrap_world/mobs.txt")
        val buffer = world.mobs.toList().joinToString("\n") {
            mapMob(it)
        }
        file.writeText(buffer)
    }

    private fun saveRooms() {
        val file = File("state/bootstrap_world/rooms.txt")
        val buffer = world.rooms.toList().joinToString("\n") {
            mapRoom(it)
        }
        file.writeText(buffer)
    }

    private fun saveDoors() {
        val file = File("state/bootstrap_world/doors.txt")
        val buffer = world.doors.toList().joinToString("") {
            mapDoor(it)
        }
        file.writeText(buffer)
    }

    private fun saveItems() {
        val file = File("state/bootstrap_world/items.txt")
        val buffer = world.items.toList().joinToString("") {
            mapItem(it)
        }
        file.writeText(buffer)
    }

    private fun saveMobResets() {
        val file = File("state/bootstrap_world/reset/mobs.txt")
        val buffer = world.mobResets.toList().joinToString("\n") {
            mapMobReset(it)
        }
        file.writeText(buffer)
    }

    private fun saveItemMobResets() {
        val file = File("state/bootstrap_world/reset/item_mobs.txt")
        val buffer = world.itemMobResets.toList().joinToString("\n") {
            mapItemMobReset(it)
        }
        file.writeText(buffer)
    }

    private fun saveItemRoomResets() {
        val file = File("state/bootstrap_world/reset/item_rooms.txt")
        val buffer = world.itemRoomResets.toList().joinToString("\n") {
            mapItemRoomReset(it)
        }
        file.writeText(buffer)
    }
}
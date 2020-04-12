package kotlinmud.fs.loader

import java.io.EOFException
import java.io.File
import kotlinmud.fs.loader.loader.DoorLoader
import kotlinmud.fs.loader.loader.ItemLoader
import kotlinmud.fs.loader.loader.Loader
import kotlinmud.fs.loader.loader.MobLoader
import kotlinmud.fs.loader.loader.RoomLoader
import kotlinmud.fs.loader.loader.reset.ItemMobResetLoader
import kotlinmud.fs.loader.loader.reset.ItemRoomResetLoader
import kotlinmud.fs.loader.loader.reset.MobResetLoader
import kotlinmud.fs.loader.mapper.DoorMapper
import kotlinmud.fs.loader.mapper.ItemMapper
import kotlinmud.fs.loader.mapper.MobMapper
import kotlinmud.fs.loader.mapper.RoomMapper
import kotlinmud.fs.loader.model.reset.ItemMobReset
import kotlinmud.fs.loader.model.reset.ItemRoomReset
import kotlinmud.fs.loader.model.reset.MobReset
import kotlinmud.item.Item
import kotlinmud.mob.Mob
import kotlinmud.world.Area
import kotlinmud.world.room.exit.Door

class AreaLoader(private val baseDir: String) {
    fun load(): Area {
        return Area(
            baseDir,
            createRoomMapper(),
            loadItems(),
            loadMobs(),
            loadMobResets(),
            loadItemMobResets(),
            loadItemRoomResets()
        )
    }

    private fun createRoomMapper(): RoomMapper {
        return RoomMapper(
            createModelList(RoomLoader(createTokenizer("$baseDir/rooms.txt"))),
            loadDoors()
        )
    }

    private fun loadItemRoomResets(): List<ItemRoomReset> {
        return createModelList(ItemRoomResetLoader(createTokenizer("$baseDir/reset/item_rooms.txt")))
    }

    private fun loadMobResets(): List<MobReset> {
        return createModelList(MobResetLoader(createTokenizer("$baseDir/reset/mobs.txt")))
    }

    private fun loadItemMobResets(): List<ItemMobReset> {
        return createModelList(ItemMobResetLoader(createTokenizer("$baseDir/reset/item_mobs.txt")))
    }

    private fun loadDoors(): List<Door> {
        return DoorMapper(
            createModelList(DoorLoader(createTokenizer("$baseDir/doors.txt")))
        ).map()
    }

    private fun loadItems(): List<Item> {
        return ItemMapper(
            createModelList(ItemLoader(createTokenizer("$baseDir/items.txt")))
        ).map()
    }

    private fun loadMobs(): List<Mob> {
        return MobMapper(
            createModelList(MobLoader(createTokenizer("$baseDir/mobs.txt")))
        ).map()
    }

    private fun createTokenizer(filename: String): Tokenizer {

        return Tokenizer(File(filename).readText())
    }
}

fun <T> createModelList(loader: Loader): List<T> {
    val models: MutableList<T> = mutableListOf()
    while (true) {
        try {
            @Suppress("UNCHECKED_CAST")
            models.add(loader.load() as T)
        } catch (e: EOFException) {
            break
        }
    }
    return models.toList()
}
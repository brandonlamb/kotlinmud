package kotlinmud.fs.loader.area.loader

import kotlinmud.fs.loader.Tokenizer
import kotlinmud.fs.loader.area.model.RoomModel
import kotlinmud.world.room.RegenLevel

class RoomLoader(private val tokenizer: Tokenizer, private val loadSchemaVersion: Int) : Loader {
    var id = 0
    var name = ""
    var description = ""
    var regen = RegenLevel.NORMAL
    var isIndoor = true
    var ownerId = 0
    var north = ""
    var south = ""
    var east = ""
    var west = ""
    var up = ""
    var down = ""
    var area = ""
    var biome = 0
    override var props: Map<String, String> = mapOf()

    override fun load(): RoomModel {
        id = tokenizer.parseInt()
        name = tokenizer.parseString()
        description = tokenizer.parseString()
        area = tokenizer.parseString()
        biome = tokenizer.parseInt()
        props = tokenizer.parseProperties()
        regen = RegenLevel.valueOf(strAttr("regen", "normal").toUpperCase())
        isIndoor = strAttr("isIndoor", "true").toBoolean()
        north = props["n"] ?: ""
        south = props["s"] ?: ""
        east = props["e"] ?: ""
        west = props["w"] ?: ""
        up = props["u"] ?: ""
        down = props["d"] ?: ""
        ownerId = intAttr("ownerId", 0)

        return RoomModel(id, name, description, regen, isIndoor, north, south, east, west, up, down, area, biome, ownerId)
    }
}

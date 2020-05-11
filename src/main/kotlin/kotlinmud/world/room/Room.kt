package kotlinmud.world.room

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.Mutable
import kotlinmud.data.Row
import kotlinmud.item.HasInventory
import kotlinmud.mob.Mob
import kotlinmud.world.biome.Biome
import kotlinmud.world.room.exit.DoorDisposition
import kotlinmud.world.room.exit.Exit

@Builder
data class Room(
    override val id: Int,
    val area: String,
    val name: String,
    val description: String,
    val regen: RegenLevel,
    val isIndoor: Boolean,
    val biome: Biome,
    @Mutable val exits: MutableList<Exit> = mutableListOf(),
    var owner: Mob?
) : Row, HasInventory {

    fun openExits(): List<Exit> {
        return exits.filter { it.door == null || it.door.disposition == DoorDisposition.OPEN }
    }
}

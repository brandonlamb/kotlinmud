package kotlinmud.mob

import kotlinmud.item.Item
import kotlinmud.world.room.Room

data class MobRoom(
    val mob: Mob,
    var room: Room,
    var disposition: Disposition = Disposition.STANDING,
    var furniture: Item? = null
)

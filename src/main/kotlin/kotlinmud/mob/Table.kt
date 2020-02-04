package kotlinmud.mob

import org.jetbrains.exposed.dao.IntIdTable

object Mobs: IntIdTable() {
    val name = varchar("name", 50)
    val description = text("description")
}

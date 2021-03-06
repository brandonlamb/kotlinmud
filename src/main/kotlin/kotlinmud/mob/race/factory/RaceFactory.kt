package kotlinmud.mob.race.factory

import kotlinmud.mob.race.impl.Avian
import kotlinmud.mob.race.impl.Canid
import kotlinmud.mob.race.impl.Deer
import kotlinmud.mob.race.impl.Dwarf
import kotlinmud.mob.race.impl.Elf
import kotlinmud.mob.race.impl.Faerie
import kotlinmud.mob.race.impl.Felid
import kotlinmud.mob.race.impl.Giant
import kotlinmud.mob.race.impl.Goat
import kotlinmud.mob.race.impl.Goblin
import kotlinmud.mob.race.impl.Human
import kotlinmud.mob.race.impl.Kender
import kotlinmud.mob.race.impl.Lasher
import kotlinmud.mob.race.impl.Ogre
import kotlinmud.mob.race.impl.Rabbit
import kotlinmud.mob.race.impl.Reptile
import kotlinmud.mob.race.impl.Undead
import kotlinmud.mob.race.type.Race

fun createRaceFromString(name: String): Race {
    return when (name) {
        "human" -> Human()
        "elf" -> Elf()
        "kender" -> Kender()
        "dwarf" -> Dwarf()
        "ogre" -> Ogre()
        "giant" -> Giant()
        "faerie" -> Faerie()
        "lasher" -> Lasher()
        "canid" -> Canid()
        "felid" -> Felid()
        "goblin" -> Goblin()
        "reptile" -> Reptile()
        "avian" -> Avian()
        "deer" -> Deer()
        "undead" -> Undead()
        "goat" -> Goat()
        "rabbit" -> Rabbit()
        else -> error("no race: $name")
    }
}

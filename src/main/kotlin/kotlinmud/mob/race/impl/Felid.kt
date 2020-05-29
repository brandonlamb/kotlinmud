package kotlinmud.mob.race.impl

import kotlinmud.attributes.Attributes
import kotlinmud.mob.fight.DamageType
import kotlinmud.mob.race.Race
import kotlinmud.mob.race.RaceType
import kotlinmud.mob.type.Form
import kotlinmud.mob.type.Size

class Felid : Race {
    override val type: RaceType = RaceType.FELID
    override val playable: Boolean = false
    override val immuneTo: List<DamageType> = listOf()
    override val resist: List<DamageType> = listOf(DamageType.DISEASE)
    override val vulnerableTo: List<DamageType> = listOf()
    override val unarmedAttackVerb: String = "bite"
    override val unarmedDamageType: DamageType = DamageType.PIERCE
    override val form: Form = Form.MAMMAL
    override val attributes: Attributes = Attributes()
    override val size: Size = Size.TINY
    override val maxAppetite: Int = 2
    override val maxThirst: Int = 1
}

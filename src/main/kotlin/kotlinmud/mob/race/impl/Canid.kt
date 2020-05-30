package kotlinmud.mob.race.impl

import kotlinmud.attributes.model.Attributes
import kotlinmud.mob.fight.DamageType
import kotlinmud.mob.race.Race
import kotlinmud.mob.race.RaceType
import kotlinmud.mob.type.Form
import kotlinmud.mob.type.Size

class Canid : Race {
    override val type: RaceType = RaceType.CANID
    override val playable: Boolean = false
    override val immuneTo: List<DamageType> = listOf()
    override val resist: List<DamageType> = listOf(DamageType.DISEASE)
    override val vulnerableTo: List<DamageType> = listOf()
    override val unarmedAttackVerb: String = "bite"
    override val unarmedDamageType: DamageType = DamageType.PIERCE
    override val form: Form = Form.MAMMAL
    override val attributes: Attributes =
        Attributes()
    override val size: Size = Size.SMALL
    override val maxAppetite: Int = 3
    override val maxThirst: Int = 3
}

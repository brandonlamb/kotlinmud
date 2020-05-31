package kotlinmud.attributes.mapper

import kotlinmud.attributes.model.Attributes
import kotlinmud.fs.str

const val LOOP_END_DELIMITER = "end"

fun mapAttributeList(attributes: List<Attributes>): String {
    return attributes.joinToString { mapAttributes(it) } + str(LOOP_END_DELIMITER)
}

fun mapAttributes(attributes: Attributes): String {
    return """${attributes.strength} ${attributes.intelligence} ${attributes.wisdom} ${attributes.dexterity} ${attributes.constitution}~
${attributes.hp} ${attributes.mana} ${attributes.mv}~
${attributes.hit} ${attributes.dam}~
${attributes.acSlash} ${attributes.acBash} ${attributes.acPierce} ${attributes.acMagic}~
"""
}

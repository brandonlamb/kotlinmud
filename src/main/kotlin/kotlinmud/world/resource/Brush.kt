package kotlinmud.world.resource

import kotlinmud.biome.type.ResourceType
import kotlinmud.helper.random.randomAmount
import kotlinmud.item.model.Item
import kotlinmud.item.service.ItemBuilderBuilder
import kotlinmud.item.type.ItemType

class Brush : Resource {
    override val resourceType: ResourceType = ResourceType.BRUSH

    override val growable: Boolean = true

    override val toughness: Int = 1

    override fun createProduct(builder: ItemBuilderBuilder): List<Item> {
        return randomAmount(3) { createItem(builder) }
    }

    private fun createItem(builder: ItemBuilderBuilder): Item {
        return builder()
            .name("small green seeds")
            .description("a handful of small green seeds are here")
            .type(ItemType.GRASS_SEED)
            .build()
    }
}

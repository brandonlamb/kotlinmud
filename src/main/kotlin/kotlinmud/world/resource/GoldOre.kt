package kotlinmud.world.resource

import kotlinmud.biome.type.ResourceType
import kotlinmud.item.model.Item
import kotlinmud.item.service.ItemBuilderBuilder
import kotlinmud.item.type.ItemType

class GoldOre : Resource {
    override val resourceType: ResourceType = ResourceType.GOLD_ORE
    override val growable: Boolean = false
    override val toughness: Int = 3

    override fun createProduct(builder: ItemBuilderBuilder): List<Item> {
        return listOf(
            builder()
                .name("a chunk of rock with hints of gold")
                .description("gold ore rock is here")
                .type(ItemType.GOLD_ORE)
                .build()
        )
    }
}

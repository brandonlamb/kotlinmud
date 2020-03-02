package kotlinmud.loader.loader

import kotlinmud.loader.Tokenizer
import kotlinmud.loader.model.ItemModel

class ItemLoader(private val tokenizer: Tokenizer) {
    var id = 0
    var name = ""
    var description = ""

    fun load(): ItemModel {
        id = tokenizer.parseId()
        name = tokenizer.parseString()
        description = tokenizer.parseString()
        tokenizer.parseProperties()

        return ItemModel(id, name, description)
    }
}
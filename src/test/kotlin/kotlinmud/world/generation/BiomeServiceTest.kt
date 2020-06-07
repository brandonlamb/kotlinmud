package kotlinmud.world.generation

import assertk.assertThat
import assertk.assertions.doesNotContain
import kotlinmud.fs.saver.layerToString
import kotlinmud.world.createBiomes
import org.junit.Test

const val WIDTH = 100
const val HEIGHT = 100
const val BIOME_COUNT = 50

class BiomeServiceTest {
    @Test
    fun testCreateLayerSanityCheck() {
        // given
        val biomeService = BiomeService(WIDTH, HEIGHT, createBiomes())

        // when
        val data = layerToString(biomeService.createLayer(BIOME_COUNT))

        // then
        assertThat(data).doesNotContain("0")
    }
}

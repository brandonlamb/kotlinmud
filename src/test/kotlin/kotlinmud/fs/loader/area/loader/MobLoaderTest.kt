package kotlinmud.fs.loader.area.loader

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import kotlinmud.fs.loader.AreaLoader
import org.junit.Test

class MobLoaderTest {
    @Test
    fun testMobLoaderLoadsRandomGold() {
        // setup
        val area = AreaLoader("test_areas/midgard", 1).load()

        // given
        val mob = area.mobs.find { it.id == 9 }!!

        // expect
        assertThat(mob.gold).isGreaterThan(0)
        assertThat(mob.gold).isLessThan(4)
    }
}

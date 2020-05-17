package kotlinmud.action.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isLessThan
import kotlinmud.attributes.Attribute
import kotlinmud.test.createTestService
import kotlinmud.world.ResourceType
import org.junit.Test

class HarvestTest {
    @Test
    fun testCanHarvestResource() {
        // setup
        val test = createTestService()
        val mob = test.createMob()
        val room = test.getStartRoom()

        // given
        room.resources.add(ResourceType.IRON_ORE)

        // when
        val response = test.runAction(mob, "harvest iron")

        // then
        assertThat(response.message.toActionCreator).isEqualTo("you successfully harvest iron ore.")
        assertThat(response.message.toObservers).isEqualTo("$mob harvests iron ore.")
        assertThat(mob.delay).isGreaterThan(0)
        assertThat(mob.mv).isLessThan(mob.calc(Attribute.MV))
    }

    @Test
    fun testCannotHarvestResourceIfDoesNotExist() {
        // setup
        val test = createTestService()
        val mob = test.createMob()

        // when
        val response = test.runAction(mob, "harvest iron")

        // then
        assertThat(response.message.toActionCreator).isEqualTo("you don't see that anywhere.")
        assertThat(mob.delay).isEqualTo(0)
        assertThat(mob.mv).isEqualTo(mob.calc(Attribute.MV))
    }
}

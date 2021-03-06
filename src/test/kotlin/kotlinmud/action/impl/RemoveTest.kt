package kotlinmud.action.impl

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import kotlinmud.item.type.Position
import kotlinmud.test.createTestService
import org.junit.Test

class RemoveTest {
    @Test
    fun testCanRemoveEquipment() {
        // setup
        val test = createTestService()

        // given
        val mob = test.createMob()
        mob.equipped.add(test.buildItem(
            test.itemBuilder()
                .position(Position.SHIELD)
                .name("a shield"), mob))
        val equippedAmount = mob.equipped.size

        // when
        val response = test.runAction(mob, "remove shield")

        // then
        assertThat(response.message.toActionCreator).isEqualTo("you remove a shield and put it in your inventory.")
        assertThat(response.message.toObservers).isEqualTo("$mob removes a shield and puts it in their inventory.")

        // and
        assertThat(mob.equipped).hasSize(equippedAmount - 1)
    }
}

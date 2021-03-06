package kotlinmud.action.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinmud.test.createTestService
import kotlinmud.test.getIdentifyingWord
import org.junit.Test

class DropTest {
    @Test
    fun testMobCanDropItem() {
        // setup
        val testService = createTestService()
        val mob = testService.createMob()
        val item = testService.createItem(mob)
        val room = testService.getRoomForMob(mob)
        val mobItemCount = testService.countItemsFor(mob)
        val roomItemCount = testService.countItemsFor(room)

        // when
        val response = testService.runAction(mob, "drop ${getIdentifyingWord(item)}")

        // then
        assertThat(response.message.toActionCreator).isEqualTo("you drop $item.")
        assertThat(testService.countItemsFor(mob)).isEqualTo(mobItemCount - 1)
        assertThat(testService.countItemsFor(room)).isEqualTo(roomItemCount + 1)
    }
}

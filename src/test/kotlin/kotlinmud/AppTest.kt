/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kotlinmud

import java.net.ServerSocket
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinmud.io.Server
import kotlinmud.service.EventService
import kotlinmud.service.MobService

class AppTest {
    @Test
    fun testAppSanityCheck() {
        // setup
        val mobService = MobService(listOf())
        val eventService = EventService()

        // when
        val app = App(eventService, mobService, Server(eventService, mobService, ServerSocket()))

        // then
        assertNotNull(app, "app should have a greeting")
    }
}

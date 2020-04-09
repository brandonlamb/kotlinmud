package kotlinmud

import java.net.ServerSocket
import kotlinmud.event.createSendMessageToRoomEvent
import kotlinmud.event.observer.Observers
import kotlinmud.event.observer.createObservers
import kotlinmud.io.ClientHandler
import kotlinmud.io.Response
import kotlinmud.io.Server
import kotlinmud.io.Syntax
import kotlinmud.loader.AreaLoader
import kotlinmud.mob.Mob
import kotlinmud.saver.WorldSaver
import kotlinmud.service.ActionService
import kotlinmud.service.EventService
import kotlinmud.service.FixtureService
import kotlinmud.service.ItemService
import kotlinmud.service.MobService
import kotlinmud.service.RespawnService
import kotlinmud.service.TimeService
import kotlinmud.service.WeatherService
import kotlinmud.world.World
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

class App(
    private val eventService: EventService,
    private val mobService: MobService,
    itemService: ItemService,
    private val server: Server
) {
    private val actionService: ActionService = ActionService(mobService, itemService, eventService, server)

    fun start() {
        println("starting app on port ${server.getPort()}")
        server.start()
        processClientBuffers()
    }

    private fun processClientBuffers() {
        while (true) {
            server.getClientsWithBuffers().forEach {
                processRequest(it)
            }
            server.pruneClients()
        }
    }

    private fun processRequest(client: ClientHandler) {
        if (client.mob.delay > 0) {
            return
        }
        val request = client.shiftBuffer()
        val response = actionService.run(request)
        eventService.publishRoomMessage(
            createSendMessageToRoomEvent(response.message, mobService.getRoomForMob(request.mob), request.mob, getTarget(response)))
    }

    private fun getTarget(response: Response): Mob? {
        return try {
            response.actionContextList.getResultBySyntax<Mob>(Syntax.MOB_IN_ROOM)
        } catch (e: Exception) {
            null
        }
    }
}

fun main() {
    val env = System.getenv("ENV")
    println("env: $env")
    val port = if (env == "ci") 0 else 9999
    createApp(port).start()
}

fun createApp(port: Int): App {
    val container = createContainer(port)
    val mobService: MobService by container.instance()
    val eventService: EventService by container.instance()
    val server: Server by container.instance()
    val respawnService: RespawnService by container.instance()
    val itemService: ItemService by container.instance()
    val observers: Observers by container.instance()
    eventService.observers = observers
    respawnService.respawn()
    return App(eventService, mobService, itemService, server)
}

fun createContainer(port: Int): Kodein {
    return Kodein {
        bind<ServerSocket>() with singleton { ServerSocket(port) }
        bind<Server>() with singleton { Server(instance<EventService>(), instance<ServerSocket>(), instance<TimeService>()) }
        bind<FixtureService>() with singleton { FixtureService() }
        bind<EventService>() with singleton { EventService() }
        bind<ItemService>() with singleton { ItemService() }
        bind<WeatherService>() with singleton { WeatherService() }
        bind<TimeService>() with singleton { TimeService(instance<EventService>()) }
        bind<ActionService>() with singleton {
            ActionService(instance<MobService>(), instance<ItemService>(), instance<EventService>(), instance<Server>())
        }
        bind<World>() with singleton {
            World(
                listOf(
                    AreaLoader("areas/midgard").load(),
                    AreaLoader("areas/midgard_castle").load(),
                    AreaLoader("areas/woods").load()
                )
            )
        }
        bind<MobService>() with singleton {
            MobService(
                instance<ItemService>(),
                instance<EventService>(),
                instance<World>()
            )
        }
        bind<RespawnService>() with singleton {
            RespawnService(
                instance<World>(),
                instance<MobService>(),
                instance<ItemService>()
            )
        }
        bind<Observers>() with singleton {
            createObservers(
                instance<Server>(),
                instance<MobService>(),
                instance<EventService>(),
                instance<RespawnService>(),
                instance<WeatherService>(),
                instance<ItemService>(),
                WorldSaver(instance<World>())
            )
        }
    }
}

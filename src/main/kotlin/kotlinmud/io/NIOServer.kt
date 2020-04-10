package kotlinmud.io

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.stream.Collectors
import kotlinmud.event.EventResponse
import kotlinmud.event.createClientConnectedEvent
import kotlinmud.event.createClientDisconnectedEvent
import kotlinmud.event.event.ClientConnectedEvent
import kotlinmud.mob.Mob
import kotlinmud.service.EventService

const val SELECT_TIMEOUT_MS: Long = 1

class NIOServer(private val eventService: EventService, val port: Int = 0) {
    private val selector: Selector = Selector.open()
    private val clients: NIOClients = mutableListOf()
    private val socket = ServerSocketChannel.open()

    fun configure() {
        val serverSocket = socket.socket()
        serverSocket.bind(InetSocketAddress(port))
        socket.configureBlocking(false)
        val ops = socket.validOps()
        socket.register(selector, ops, null)
    }

    fun removeDisconnectedClients() {
        val lost = clients.stream()
            .filter { !it.connected }
            .collect(Collectors.toList())
        lost.forEach {
            eventService.publish<NIOClient, NIOClient>(createClientDisconnectedEvent(it))
        }
        clients.removeAll(lost)
    }

    fun readIntoBuffers() {
        selector.select(SELECT_TIMEOUT_MS)
        val selectedKeys: MutableSet<SelectionKey> = selector.selectedKeys()
        val i = selectedKeys.iterator()
        while (i.hasNext()) {
            val key = i.next()
            if (key.isAcceptable) {
                // New client has been accepted
                handleAccept(socket)
            } else if (key.isReadable) {
                // We can run non-blocking operation READ on our client
                handleRead(key)
            }
            i.remove()
        }
    }

    fun getClients(): NIOClients {
        return clients
    }

    fun getClientsWithBuffers(): NIOClients {
        return clients.stream().filter { it.buffers.isNotEmpty() }.collect(Collectors.toList())
    }

    fun getClientForMob(mob: Mob): NIOClient? {
        return clients.find { it.mob == mob }
    }

    fun getClientsFromMobs(mobs: List<Mob>): NIOClients {
        return mobs.mapNotNull { mob ->
            clients.find { it.mob == mob }
        }.toMutableList()
    }

    private fun handleAccept(mySocket: ServerSocketChannel) {
        // Accept the connection and set non-blocking mode
        val socket = mySocket.accept()
        socket.configureBlocking(false)

        // Register that client is reading this channel
        socket.register(selector, SelectionKey.OP_READ)
        val client = NIOClient(socket)
        eventService.publish<ClientConnectedEvent, EventResponse<Mob>>(createClientConnectedEvent(client))
        clients.add(client)
        println("connection accepted :: ${socket.remoteAddress}")
    }

    private fun handleRead(key: SelectionKey) {
        // create a ServerSocketChannel to read the request
        val socket = key.channel() as SocketChannel

        // Create buffer to read data
        val buffer = ByteBuffer.allocate(1024)
        socket.read(buffer)

        // Parse data from buffer to String
        val data: String = String(buffer.array()).trim { it <= ' ' }

        if (data.isNotEmpty()) {
            val client = getClientBySocket(socket)
            client.buffers.add(data)
            if (data.equals("exit", ignoreCase = true)) {
                socket.close()
                println("connection closed :: ${socket.remoteAddress}")
            }
        }
    }

    private fun getClientBySocket(socket: SocketChannel): NIOClient {
        return clients.find { it.socket == socket }!!
    }
}

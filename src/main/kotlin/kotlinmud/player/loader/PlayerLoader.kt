package kotlinmud.player.loader

import java.io.EOFException
import java.io.FileNotFoundException
import kotlinmud.fs.factory.playerFile
import kotlinmud.fs.loader.Tokenizer
import kotlinmud.player.model.Player
import kotlinmud.player.model.PlayerBuilder
import org.joda.time.LocalDate

class PlayerLoader(private val tokenizer: Tokenizer) {
    companion object {
        fun loadAllPlayers(): List<Player> {
            return try {
                val tokenizer = Tokenizer(playerFile().readText())
                val loader = PlayerLoader(tokenizer)
                val players = mutableListOf<Player>()
                while (true) {
                    try {
                        players.add(loader.load())
                    } catch (e: EOFException) {
                        break
                    }
                }
                players
            } catch (e: FileNotFoundException) {
                listOf()
            }
        }
    }

    fun load(): Player {
        return PlayerBuilder()
            .email(tokenizer.parseString())
            .name(tokenizer.parseString())
            .created(LocalDate.parse(tokenizer.parseString()))
            .mobs(tokenizer.parseString().split(",").toMutableList())
            .build()
    }
}

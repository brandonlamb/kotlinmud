package kotlinmud.fs.service

import java.io.File
import kotlinmud.fs.constant.CURRENT_LOAD_SCHEMA_VERSION
import kotlinmud.fs.constant.CURRENT_WRITE_SCHEMA_VERSION
import kotlinmud.fs.factory.timeFile
import kotlinmud.fs.factory.versionFile
import kotlinmud.fs.loader.AreaLoader
import kotlinmud.fs.loader.Tokenizer
import kotlinmud.fs.saver.WorldSaver
import kotlinmud.service.TimeService
import kotlinmud.world.model.Area
import kotlinmud.world.model.World
import org.slf4j.LoggerFactory

class PersistenceService(
    private val previousLoadSchemaVersion: Int,
    private val previousWriteSchemaVersion: Int
) {
    private val logger = LoggerFactory.getLogger(PersistenceService::class.java)
    val loadSchemaToUse = CURRENT_LOAD_SCHEMA_VERSION.coerceAtLeast(previousWriteSchemaVersion)

    init {
        logger.info("previous load schema: {}, write schema: {}", previousLoadSchemaVersion, previousWriteSchemaVersion)
        logger.info("hardcoded overrides :: load schema: {}, write schema: {}",
            CURRENT_LOAD_SCHEMA_VERSION,
            CURRENT_WRITE_SCHEMA_VERSION
        )
    }

    fun writeVersionFile() {
        logger.info("version file :: write schema {}, load schema: {}",
            CURRENT_WRITE_SCHEMA_VERSION,
            CURRENT_LOAD_SCHEMA_VERSION
        )
        versionFile().writeText("""#$CURRENT_LOAD_SCHEMA_VERSION
#$CURRENT_WRITE_SCHEMA_VERSION""")
    }

    fun writeTimeFile(timeService: TimeService) {
        logger.info("time file :: {}", timeService.getTime())
        timeFile().writeText("#${timeService.getTime()}")
    }

    fun writeWorld(world: World) {
        logger.info("areas file :: write schema {}",
            CURRENT_WRITE_SCHEMA_VERSION
        )
        WorldSaver(world).save()
    }

    fun loadTimeFile(): Int {
        val file = timeFile()
        return if (file.exists()) Tokenizer(file.readText()).parseInt() else 0
    }

    fun loadAreas(isTest: Boolean): List<Area> {
        return if (File("state").exists() && !isTest) {
            getLiveAreaData()
        } else if (isTest) {
            getTestAreaData()
        } else {
            getBootstrapAreaData()
        }
    }

    private fun getLiveAreaData(): List<Area> {
        return listOf(AreaLoader("state/bootstrap_world", loadSchemaToUse).load())
    }

    private fun getTestAreaData(): List<Area> {
        return listOf(
            AreaLoader("test_areas/midgard", loadSchemaToUse).load(),
            AreaLoader("test_areas/midgard_castle", loadSchemaToUse).load(),
            AreaLoader("test_areas/woods", loadSchemaToUse).load()
        )
    }

    private fun getBootstrapAreaData(): List<Area> {
        return listOf(
            AreaLoader("bootstrap_world/midgard", loadSchemaToUse).load(),
            AreaLoader("bootstrap_world/midgard_castle", loadSchemaToUse).load(),
            AreaLoader("bootstrap_world/woods", loadSchemaToUse).load()
        )
    }
}

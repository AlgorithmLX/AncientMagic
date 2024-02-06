package team._0mods.aeternus

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val ModId = "aeternus"
const val ModName = "Aeternus"

fun resloc(id: String, path: String) = ResourceLocation(id, path)

val String.rl: ResourceLocation
    get() = ResourceLocation(this)

fun String.toRL(): ResourceLocation = ResourceLocation(this)

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const
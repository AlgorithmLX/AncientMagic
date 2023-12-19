package team.zeds.aeternus.provider

import team.zeds.aeternus.LOGGER
import team.zeds.aeternus.provider.core.ITabHelper
import java.util.ServiceLoader
import kotlin.reflect.KClass

object ServiceProvider {
    val tabHelper: ITabHelper = initPlatformed(ITabHelper::class)

    fun <T> initPlatformed(clazz: KClass<T>): T where T: Any {
        val loaded: T = ServiceLoader.load(clazz.java).findFirst().orElseThrow { NullPointerException("Failed to load Service for ${clazz.simpleName}") }
        LOGGER.debug("Loading service {} for {}...", loaded, clazz)
        return loaded
    }
}
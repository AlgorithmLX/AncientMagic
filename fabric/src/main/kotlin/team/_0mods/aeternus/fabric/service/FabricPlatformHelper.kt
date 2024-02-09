package team._0mods.aeternus.fabric.service

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import team._0mods.aeternus.service.core.IPlatformHelper
import kotlin.jvm.optionals.getOrNull

class FabricPlatformHelper: IPlatformHelper {
    override fun isProduction(): Boolean = !FabricLoader.getInstance().isDevelopmentEnvironment

    override fun isLogicalClient(): Boolean = Minecraft.getInstance().level != null

    override fun isPhysicalClient(): Boolean = FabricLoader.getInstance().environmentType == EnvType.CLIENT

    override fun isModLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)

    override fun getModNameByModId(modId: String): String {
        // yeah, it's very impractical, but sorry, I really want to do it >_<
        val cat = StringBuilder()
        cat.append("\n")
        cat.append("　　　　　／＞　　フ").append("\n")
        cat.append("　　　　　| 　_　 _ l").append("\n")
        cat.append("　 　　　／` ミ＿xノ").append("\n")
        cat.append("　　 　 /　　　 　 |").append("\n")
        cat.append("　　　 /　 ヽ　　 ﾉ").append("\n")
        cat.append("　 　 │　　| | |")
        val builtCat = cat.toString()
        val failedName = "Mod Name for Mod ID: $modId is not loaded! It is so sad :($builtCat"

        return FabricLoader.getInstance().getModContainer(modId).getOrNull()?.metadata?.name ?: failedName
    }

    override fun isFabric(): Boolean = true
}
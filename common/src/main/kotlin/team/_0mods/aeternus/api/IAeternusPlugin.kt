package team._0mods.aeternus.api

import team._0mods.aeternus.api.registry.IResearchRegistry
import team._0mods.aeternus.api.registry.IResearchTriggerRegistry

annotation class AeternusPlugin

interface IAeternusPlugin {
    val modId: String

    fun registerResearch(reg: IResearchRegistry) {}

    fun registerResearchTriggers(reg: IResearchTriggerRegistry) {}
}
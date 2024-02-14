package team._0mods.aeternus.fabric.service

import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.api.gui.screen.ScreenHooks
import team._0mods.aeternus.fabric.api.event.EventFactoryImpl
import team._0mods.aeternus.fabric.api.event.EventHandlerImpl
import team._0mods.aeternus.service.core.IEventHelper

class FabricEventHelper: IEventHelper {
    override val screenHooks: ScreenHooks
        get() = TODO("Not yet implemented")

    override val eventHandler: EventHandler
        get() = EventHandlerImpl

    override val eventFactory: EventFactory
        get() = EventFactoryImpl
}
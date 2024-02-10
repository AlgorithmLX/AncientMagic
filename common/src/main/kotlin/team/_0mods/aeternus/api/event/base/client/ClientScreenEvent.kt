package team._0mods.aeternus.api.event.base.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder
import team._0mods.aeternus.api.gui.event.ScreenAccess

interface ClientScreenEvent {
    companion object {
        @JvmField /* no getter generate */ val RENDER_GUI_OVERLAY = EventFactory.createLoop<PostRenderGuiOverlay>()

        @JvmField val DEBUG_LEFT = EventFactory.createLoop<DebugText>()
        @JvmField val DEBUG_RIGHT = EventFactory.createLoop<DebugText>()

        @JvmField val INIT_PRE = EventFactory.createEventResult<ScreenInitPre>()
        @JvmField val INIT_POST = EventFactory.createLoop<ScreenInitPost>()

        @JvmField val RENDER_PRE = EventFactory.createEventResult<ScreenRenderPre>()
        @JvmField val RENDER_POST = EventFactory.createLoop<ScreenRenderPost>()

        @JvmField val RENDER_CONTAINER_BACKGROUND = EventFactory.createLoop<ContainerMenuRenderBackground>()
        @JvmField val RENDER_CONTAINER_FOREGROUND = EventFactory.createLoop<ContainerMenuRenderForeground>()

        @JvmField val SET_SCREEN = EventFactory.createLoop<SetScreen>()
    }

    interface PostRenderGuiOverlay {
        fun render(stack: PoseStack, partialTick: Float)
    }

    interface DebugText {
        fun render(texts: List<String>)
    }

    interface ScreenInitPre {
        fun init(screen: Screen, access: ScreenAccess): EventResult
    }

    interface ScreenInitPost {
        fun init(screen: Screen, access: ScreenAccess)
    }

    interface ScreenRenderPre {
        fun render(screen: Screen, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float): EventResult
    }

    interface ScreenRenderPost {
        fun render(screen: Screen, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    interface ContainerMenuRenderBackground {
        fun render(screen: AbstractContainerScreen<*>, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    interface ContainerMenuRenderForeground {
        fun render(screen: AbstractContainerScreen<*>, graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float)
    }

    interface SetScreen {
        fun setScreen(screen: Screen): EventResultHolder<Screen>
    }
}
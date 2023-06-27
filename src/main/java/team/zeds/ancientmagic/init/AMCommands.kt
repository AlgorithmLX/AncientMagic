package team.zeds.ancientmagic.init

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.RegisterCommandsEvent
import team.zeds.ancientmagic.api.magic.MagicType
import team.zeds.ancientmagic.api.mod.Constant
import team.zeds.ancientmagic.capability.PlayerMagicCapability
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier

object AMCommands {
    @JvmField
    val NAMES_OF_COMMAND = arrayOf(
        "am",
        Constant.KEY,
        "ancient",
        "magicancient"
    )

    @JvmStatic
    fun registerCommands(e: RegisterCommandsEvent) {
        commandRegister(e.dispatcher)
    }

    @JvmStatic
    private fun commandRegister(sourceStack: CommandDispatcher<CommandSourceStack>) {
        val names =
            if (AMManage.COMMON_CONFIG.VALID_COMMAND_NAMES != null) AMManage.COMMON_CONFIG.VALID_COMMAND_NAMES!!.get() else NAMES_OF_COMMAND
        for (name in names) {
            sourceStack.register(
                Commands.literal(name)
                    .then(registerSetStage())
            )
        }
    }

    @JvmStatic
    private fun registerSetStage(): ArgumentBuilder<CommandSourceStack?, *>? {
        return Commands.literal("setStage")
            .requires { req: CommandSourceStack -> req.hasPermission(Commands.LEVEL_ADMINS) }
            .then(
                Commands.argument("players", EntityArgument.players())
                    .then(Commands.argument("levels", IntegerArgumentType.integer(0, 4)))
            )
            .executes { cmd: CommandContext<CommandSourceStack> ->
                setLevel(
                    cmd.source, EntityArgument.getPlayers(cmd, "players"),
                    IntegerArgumentType.getInteger(cmd, "levels")
                )
            }
    }

    @JvmStatic
    private fun setLevel(sourceStack: CommandSourceStack, players: Collection<ServerPlayer>, countOfLevels: Int): Int {
        val returnValue = AtomicInteger()
        for (player in players) {
            player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent { cap: PlayerMagicCapability ->
                val iValue = cap.magicLevel
                if (iValue == countOfLevels && countOfLevels == 4) {
                    command("max").get()?.let { sourceStack.sendFailure(it) }
                    returnValue.set(0)
                } else if (iValue < countOfLevels) {
                    cap.setLevel(countOfLevels)
                    sourceStack.sendSuccess(command("success", countOfLevels), false)
                    returnValue.set(players.size)
                }
            }
        }
        return returnValue.get()
    }

    @JvmStatic
    private fun command(message: String, vararg objs: Any): Supplier<Component?> {
        return Supplier {
            MagicType.getMagicMessage(
                String.format(
                    "level.command.%s",
                    message
                ), *objs
            )
        }
    }
}
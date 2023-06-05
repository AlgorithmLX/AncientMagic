package team.zeromods.ancientmagic.event.forge;

import api.ancientmagic.item.MagicItem;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import team.zeromods.ancientmagic.init.AMCapability;
import team.zeromods.ancientmagic.capability.PlayerMagicCapability;
import team.zeromods.ancientmagic.compact.CompactInitializer;
import team.zeromods.ancientmagic.init.AMNetwork;
import team.zeromods.ancientmagic.init.config.AMCommon;
import team.zeromods.ancientmagic.init.AMTags;
import team.zeromods.ancientmagic.network.PlayerMagicDataSyncS2CPacket;

import java.util.Objects;

public class MagicData {
    public static void tooltipEvent(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();

        if (e.getItemStack().getItem() instanceof MagicItem item) {
            if (item.getMagicType() != null)
                tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != null)
                tooltip.add(Component.translatable("magicType.subtype", item.getMagicSubtype().getTranslation()));

            if (item.getMaxMana(stack, null) != 0)
                tooltip.add(MagicType.getMagicMessage("storage", item.getStorageMana(stack, null),
                        item.getMaxMana(stack, null)));

            var resource = ForgeRegistries.ITEMS.getKey(item);
            assert resource != null;
            var namespace = resource.getNamespace();

            if (!namespace.equals(Constant.Key))
                tooltip.add(Component.translatable(String.format("content.%s.added_by", Constant.Key), namespace));

            if (CompactInitializer.getWaystonesLoaded() && ((AMCommon.COMPACT_WAYSTONES != null
                    && AMCommon.COMPACT_WAYSTONES.get()) && FMLEnvironment.production) &&
                    (stack.is(AMTags.CONSUMABLE_TELEPORTATION_CATALYST)
                    || stack.is(AMTags.UNCONSUMABLE_TELEPORTATION_CATALYST))) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", Constant.Key)));
            }
        }
    }

    public static void playerEvent(PlayerEvent event) {
        var player = event.getEntity();

        if (player != null) {
            var stack = player.getItemInHand(InteractionHand.MAIN_HAND);


            if (stack.getItem() instanceof MagicItem item) {
                player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
                    var magicLevel = cap.getMagicLevel();

                    if (magicLevel >= (Objects.requireNonNull(item.getMagicType()).numerate())) {
                        item.canUseItem = true;
                    } else {
                        player.displayClientMessage(MagicType.getMagicMessage("notLevel",
                                        item.getMagicType().getTranslation(),
                                        MagicTypes.getByNumeration(magicLevel).getTranslation()),
                                true);
                        item.canUseItem = false;
                    }

                });
            }

        }
    }

    public static void registerCapability(final RegisterCapabilitiesEvent e) {
        e.register(PlayerMagicCapability.class);
    }

    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath())
            event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(old ->
                    event.getOriginal().getCapability(AMCapability.PLAYER_MAGIC_HANDLER)
                            .ifPresent(newCap -> newCap.copyFrom(old)));

    }

    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(AMCapability.PLAYER_MAGIC_HANDLER).isPresent())
                event.addCapability(AMCapability.PLAYER_MAGIC_HANDLER_ID, new PlayerMagicCapability.Provider());
        }
    }

    public static void playerTick(final TickEvent.PlayerTickEvent e) {
        if (e.side == LogicalSide.SERVER) {
            e.player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->
                AMNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayer) e.player), new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel()))
            );
        }
    }

    public static void playerConnectToWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap ->//MagicData:122
                    AMNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), new PlayerMagicDataSyncS2CPacket(cap.getMagicLevel())) //MagicData:123
                );
            }
        }
    }
}
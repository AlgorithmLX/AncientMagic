package team.zeds.ancientmagic.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import team.zeds.ancientmagic.AMConstant;
import team.zeds.ancientmagic.api.item.MagicBlockItem;
import team.zeds.ancientmagic.api.item.MagicItem;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.init.config.AMConfig;
import team.zeds.ancientmagic.init.registries.AMTags;
import team.zeds.ancientmagic.platform.AMServices;

import java.util.List;

import static team.zeds.ancientmagic.api.magic.MagicType.getMagicMessage;

public final class AMCommonnessEvents {
    public static void tooltipEvent(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        if (stack.getItem() instanceof MagicItem item) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != MagicTypes.NOTHING) tooltip.add(
                    Component.translatable(
                            "magicType.subtype",
                            item.getMagicSubtype().getTranslation()
                    )
            );

            if (item.getMaxMana() != 0) tooltip.add(
                    getMagicMessage(
                            "storage", item.getManaStorages(stack),
                            item.getMaxMana()
                    )
            );

            final var resource = BuiltInRegistries.ITEM.getKey(item);
            final var namespace = resource.getNamespace();

            if (!namespace.equals(AMConstant.KEY)) tooltip.add(
                    Component.translatable(
                            String.format(
                                    "content.%s.added_by",
                                    AMConstant.KEY
                            ), namespace
                    )
            );

            if (AMServices.PLATFORM.isDeveloperment() && AMServices.PLATFORM.isModLoaded("waystones") &&
                    AMConfig.getCommon().isWayStoneCompact() /*&&
                (stack.`is`(AMTags.getInstance().getUnconsumeCatalyst())
                        || stack.`is`(AMTags.getInstance().getConsumeCatalyst()))*/
            ) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", AMConstant.KEY)));
            }
        }

        if (stack.getItem() instanceof MagicBlockItem item) {
            tooltip.add(Component.translatable("magicType.type", item.getMagicType().getTranslation()));
            if (item.getMagicSubtype() != MagicTypes.NOTHING) tooltip.add(
                    Component.translatable(
                            "magicType.subtype",
                            item.getMagicSubtype().getTranslation()
                    )
            );

            if (item.getMaxMana() != 0) tooltip.add(
                    getMagicMessage(
                            "storage", item.getManaStorages(stack),
                            item.getMaxMana()
                    )
            );

            final var resource = BuiltInRegistries.ITEM.getKey(item);
            final var namespace = resource.getNamespace();

            if (!namespace.equals(AMConstant.KEY)) tooltip.add(
                    Component.translatable(
                            String.format(
                                    "content.%s.added_by",
                                    AMConstant.KEY
                            ), namespace
                    )
            );

            if (AMServices.PLATFORM.isDeveloperment() && AMServices.PLATFORM.isModLoaded("waystones") &&
                    AMConfig.getCommon().isWayStoneCompact() &&
                (stack.is(AMTags.getInstance().getUnconsumeCatalyst())
                        || stack.is(AMTags.getInstance().getConsumeCatalyst()))
            ) {
                tooltip.add(Component.translatable(String.format("compact.%s.waystones.tpItem", AMConstant.KEY)));
            }
        }
    }

    public static void playerTick(Player player) {
        final var stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        final var cap = AMServices.PLATFORM.getPlayerMagic(player);

        if (stack.getItem() instanceof MagicItem || stack.getItem() instanceof MagicBlockItem) {
            final var item = (MagicItem) stack.getItem();
            final var blockItem = (MagicBlockItem) stack.getItem();

            if (cap.getMagicLevel() >= item.getBuilder().getMagicType().asLevel()) {
                item.setItemUse(true);
            } else {
                if (player.level().isClientSide()) {
                    player.displayClientMessage(
                            Component.translatable(
                                    "magic.ancientmagic.notLevel",
                                    item.getMagicType().getTranslation(),
                                    MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()
                            ), true
                    );
                    item.setItemUse(false);
                }
            }

            if (cap.getMagicLevel() >= blockItem.getBuilder().getMagicType().asLevel()) {
                blockItem.setItemUse(true);
            } else {
                if (player.level().isClientSide()) {
                    player.displayClientMessage(
                            Component.translatable(
                                    "magic.ancientmagic.notLevel",
                                    blockItem.getMagicType().getTranslation(),
                                    MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation()
                            ), true
                    );
                    blockItem.setItemUse(false);
                }
            }
        }

        if (!player.level().isClientSide())
            if (player instanceof ServerPlayer sp)
                AMServices.PLATFORM.getIAMNetwork().sendToPlayer(AMServices.PLATFORM.getS2CPlayerPacket(), sp);

    }

    public static void playerClone(Player original, boolean wasDeath) {
        final var old = AMServices.PLATFORM.getOldPlayerMagic(original);
        final var newCpt = AMServices.PLATFORM.getNewPlayerMagic(original);
        if (wasDeath) {
            newCpt.copy(old);
        }
    }

    public static void playerConnectToLevel(Level level, LivingEntity entity) {
        if (!level.isClientSide()) {
            if (entity instanceof ServerPlayer sp) {
                AMConstant.LOGGER.debug("Player {} connected!", sp);

                AMServices.PLATFORM.getIAMNetwork().sendToPlayer(AMServices.PLATFORM.getS2CPlayerPacket(), sp);
            }
        }
    }
}
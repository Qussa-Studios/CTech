package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Interface.Screen.ZettaMenu;
import QQussa.ctech.Interface.Screen.ZettaScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ZettaClass extends Block {
    public ZettaClass(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.disconnect(Component.literal("DON'T TOUCH ME LIKE THAT!!!"));
        }
        super.playerDestroy(level, player, blockPos, state, blockEntity, itemStack);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (!level.isClientSide && livingEntity instanceof ServerPlayer serverPlayer) {
            level.destroyBlock(blockPos,true);
            serverPlayer.connection.disconnect(Component.literal("DON'T PLACE ME LIKE THAT!!!"));
        }
        super.setPlacedBy(level, blockPos, state, livingEntity, itemStack);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            Inventory playerInv = player.getInventory();
            ZettaMenu menu = new ZettaMenu(0, playerInv);
            Minecraft.getInstance().tell(() -> {
                Minecraft.getInstance().setScreen(new ZettaScreen(menu, playerInv, Component.literal("Zetta"))
                );
            });
        }
        return InteractionResult.SUCCESS;
    }
}

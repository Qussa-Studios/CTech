package QQussa.ctech.Item.Class;

import QQussa.ctech.Blocks.Entity.SocketEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


public class CoilClass extends Item {

    public CoilClass(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof SocketEntity entity) {
                BlockPos savedPos = getConnectionPos(itemStack);
                if (savedPos == null) {
                    saveConnect(itemStack, pos);
                    player.sendSystemMessage(Component.translatable("message.ctech.pos_saved"));
                    return InteractionResult.SUCCESS;
                } else {
                    if (level.getBlockEntity(savedPos) instanceof SocketEntity entity1) {
                        boolean result1 = entity.addConnection(savedPos,level);
                        boolean result2 = entity1.addConnection(pos,level);
                        if (result1&&result2) {
                            player.sendSystemMessage(Component.translatable("message.ctech.connected"));
                            clearConnection(itemStack);
                        } else {
                            clearConnection(itemStack);
                            player.sendSystemMessage(Component.translatable("message.ctech.already_connected"));
                            return InteractionResult.SUCCESS;
                        }
                    } else {
                        player.sendSystemMessage(Component.translatable("message.ctech.first_block_destroyed"));
                        clearConnection(itemStack);
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.SUCCESS;
                }
            } else {
                player.sendSystemMessage(Component.translatable("message.ctech.invalid_block"));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public void saveConnect(ItemStack stack, BlockPos pos) {
        CompoundTag tag = stack.getOrCreateTag();
        BlockPos immutablePos = pos.immutable();
        tag.putInt("saved_x", pos.getX());
        tag.putInt("saved_y", pos.getY());
        tag.putInt("saved_z", pos.getZ());
    }

    private BlockPos getConnectionPos(ItemStack stack) {
        if (!stack.hasTag()) {
            return null;
        }
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("saved_x")) {
            return new BlockPos(
                    tag.getInt("saved_x"),
                    tag.getInt("saved_y"),
                    tag.getInt("saved_z")
            );
        }
        return null;
    }

    private void clearConnection(ItemStack stack) {
        stack.getOrCreateTag().remove("saved_x");
        stack.getOrCreateTag().remove("saved_y");
        stack.getOrCreateTag().remove("saved_z");
    }
}

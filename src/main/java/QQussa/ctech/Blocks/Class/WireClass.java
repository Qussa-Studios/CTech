package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Blocks.Entity.WireEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class WireClass extends Block implements EntityBlock {
    public WireClass(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WireEntity(blockPos, blockState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof WireEntity currentWire) {
                currentWire.updateConnections();

                for (Direction dir : Direction.values()) {
                    if (pLevel.getBlockEntity(pPos.relative(dir)) instanceof WireEntity neighborWire) {
                        neighborWire.updateConnections();
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof WireEntity wire) {
                int voltage = wire.getVoltage();
                int amperage = wire.getAmperage();
                Set<BlockPos> conn = wire.getConnectedBlocks();

                pPlayer.sendSystemMessage(Component.literal("voltage: "+voltage+"V. amperage: "+amperage+"A. connections:" +conn));
            }
        }
        return InteractionResult.SUCCESS;
    }
}

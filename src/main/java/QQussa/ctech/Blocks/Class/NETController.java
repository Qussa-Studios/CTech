package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Blocks.Entity.NETControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class NETController extends Block implements EntityBlock {
    public NETController(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new NETControllerEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (pLevel.getBlockEntity(pPos) instanceof NETControllerEntity entity) {
            if (pPlayer.isShiftKeyDown()) {
                pPlayer.sendSystemMessage(Component.literal("ПАДКЛЮЧЕНИЯ!!!! " + entity.getNet()));
            } else {
                entity.crateNet(pLevel, GlobalPos.of(pLevel.dimension(),pPos));
            }
        }
        return InteractionResult.SUCCESS;
    }
}

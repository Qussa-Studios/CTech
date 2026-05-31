package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Blocks.Entity.RouterEntity;
import QQussa.ctech.Electrical.IUniversalEnergy;
import net.minecraft.core.BlockPos;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RouterClass extends Block implements EntityBlock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterClass.class);

    public RouterClass(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RouterEntity(blockPos, blockState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.getBlockEntity(pPos) instanceof RouterEntity router) {
            final Set<BlockPos> ignorePos = new HashSet<>();
            ignorePos.add(pPos);
            router.updateNet(ignorePos);
            LOGGER.debug("Блок поставлен и обновлен");
            return;
        }
        LOGGER.debug("Блок поставлен но не был обновлен");
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof RouterEntity router) {
            final Set<BlockPos> ignorePos = new HashSet<>();
            ignorePos.add(pPos);

            router.updateNet(ignorePos);
            LOGGER.debug("Блок сломан и обновлен");
            return;
        }
        LOGGER.debug("Блок сломан но не был обновлен");
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (pLevel.getBlockEntity(pPos) instanceof IUniversalEnergy energy) {
            pPlayer.sendSystemMessage(Component.literal("Net: " + energy.getNet() + " Connect: " + energy.getNet().getConnectedBlock()));
        }
        return InteractionResult.SUCCESS;
    }
}

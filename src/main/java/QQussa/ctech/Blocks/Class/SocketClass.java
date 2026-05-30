package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Blocks.Entity.SocketEntity;
import QQussa.ctech.Electrical.ElectricalNet;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
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

import java.util.Set;


public class SocketClass extends Block implements EntityBlock {
    public SocketClass(Properties properties) {
        super(properties);
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof SocketEntity wire) {
                if (wire.getNet() != null)  {
                    double v = wire.getNet().getVoltage();
                    double a = wire.getNet().getAmperage();
                    Set<BlockPos> conn = wire.getConnection();
                    LOGGER.info("§a[CTech] §eВольтаж: §f" + v + "V §7| §eТок: §f" + a + "A");
                    LOGGER.info("§a[CTech] §eСоединения: §f" + conn);
                    return InteractionResult.SUCCESS;
                }else {
                    LOGGER.info("§a[CTech] Нет Сети");
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof SocketEntity wire) {
                wire.setNet(new ElectricalNet());
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SocketEntity(blockPos,blockState);
    }
}

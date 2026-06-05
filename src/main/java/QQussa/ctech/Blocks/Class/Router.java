package QQussa.ctech.Blocks.Class;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Blocks.Entity.RouterEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Router extends Block implements EntityBlock {
    public Router(Properties pProp) {
        super(pProp);
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RouterEntity(pPos,pState);
    }
}

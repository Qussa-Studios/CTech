package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Properties;

public class WireEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    public WireEntity(BlockPos blockPos, BlockState state) {
        super(BlockRegister.WIRE_ENTITY.get(),blockPos,state);
        LOGGER.info("!!! [CTECH] СПАВН БЛОК-ЭНТИТИ НА КООРДИНАТАХ: " + blockPos);
    }
}

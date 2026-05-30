package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

public class WireEntity extends BlockEntity {
    private int voltage;
    private int amperage;

    //Список подключенных блоков
    private final Set<BlockPos> connectedBlocks = new HashSet<>();

    public WireEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockRegister.WIRE_ENTITY.get(), blockPos, blockState);
    }

    // Возвращаем пул всех активных соединений
    public Set<BlockPos> getConnectedBlocks() {
        return this.connectedBlocks;
    }

    public void updateConnections() {
        this.connectedBlocks.clear();
        for (Direction dir : Direction.values()) {
            BlockPos neighborWire = this.worldPosition.relative(dir);
            if (this.level.getBlockEntity(neighborWire) instanceof WireEntity wire) {
                this.connectedBlocks.add(wire.worldPosition.immutable());
            }
        }
    }
    @Override
    public void onLoad() {
        super.onLoad();
        updateConnections();
    }

    // Устанавливаем энергию себе, а так же остальным членам энергосети, находящиеся в пуле соединений
    public void setEnergy(Level level, BlockPos pos, Set<BlockPos> blockHistory, int amperage, int voltage) {
        this.amperage = amperage;
        this.voltage = voltage;
        blockHistory.add(this.worldPosition.immutable());
        for (BlockPos blockPos : this.connectedBlocks) {
            if (blockHistory.contains(blockPos)) {
                continue;
            }
            if (level.getBlockEntity(blockPos) instanceof WireEntity entity) {
                entity.setEnergy(level, blockPos, blockHistory, amperage, voltage);
            }
        }
    }

    public int getAmperage() {
        return this.amperage;
    }

    public int getVoltage() {
        return this.voltage;
    }
}

package QQussa.ctech.Electrical;

import QQussa.ctech.Blocks.Entity.SocketEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Set;

public class ElectricalNet {
    private double voltage = (int) (Math.random() * 100);
    private double amperage = (float) (Math.random() * 100);

    private Set<BlockPos> blocks;

    public ElectricalNet() {
    }

    public void merage(ElectricalNet net, Level level) {
        for (BlockPos pos : net.getBlocks()) {

            if (level.getBlockEntity(pos) instanceof SocketEntity entity) {
                entity.setNet(this);
            }

            this.blocks.add(pos);
        }
    }

    public Set<BlockPos> getBlocks() {
        return this.blocks;
    }

    public double getAmperage() {
        return this.amperage;
    }
    public double getVoltage() {
        return this.voltage;
    }
}

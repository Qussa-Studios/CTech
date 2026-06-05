package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Electrical.EnergyBus;
import QQussa.ctech.Electrical.IUniversalEnergy;
import QQussa.ctech.Electrical.eType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class RouterEntity extends BlockEntity implements IUniversalEnergy {
    public RouterEntity(BlockPos pPos, BlockState pState) {
        super(BlockRegister.ROUTER_ENTITY.get(),pPos,pState);
    }

    private EnergyBus net;
    private Set<BlockPos> connections;
    // === IUniversalEnergy ====

    @Override
    public void ping(Set<BlockPos> pSetPos) {
        if (this.level == null) {return;}
        pSetPos.add(this.worldPosition);
        for (BlockPos conn : connections) {
            if (this.level.getBlockEntity(conn) instanceof IUniversalEnergy iue) {
                iue.ping(pSetPos);
            }
        }
    }

    @Override
    public boolean setNet(EnergyBus pNet) {
        this.net = pNet;
        return true;
    }

    @Override
    public EnergyBus getNet() {
        return this.net;
    }

    @Override
    public Set<BlockPos> getConnections() {
        return this.connections;
    }

    @Override
    public boolean addConnect(BlockPos pPos) {
        return this.connections.add(pPos);
    }

    @Override
    public eType getEnergyType() {
        return eType.ROUTER;
    }
}

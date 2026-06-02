package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Electrical.EnergyBus;
import QQussa.ctech.Electrical.IUniversalEnergy;
import QQussa.ctech.Electrical.eType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NETControllerEntity extends BlockEntity implements IUniversalEnergy {
    private static final Logger LOGGER = LoggerFactory.getLogger(NETControllerEntity.class);
    private final eType type = eType.NET_CONTROLLER;
    private EnergyBus net;

    public NETControllerEntity(BlockPos pos, BlockState state) {
        super(BlockRegister.NET_CONTROLLER_ENTITY.get(),pos,state);
    }

    // === IUniversalEnergy ===

    @Override
    public EnergyBus getNet() {
        return this.net;
    }

    @Override
    public boolean setNet(EnergyBus net) {
        this.net = net;
        return true;
    }

    @Override
    public eType getEnergyType() {
        return this.type;
    }

    // === NetController ===

    public boolean crateNet(Level pLevel, GlobalPos pGPos) {
        return EnergyBus.create(pLevel,pGPos);
    }
}

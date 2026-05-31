package QQussa.ctech.Electrical;

import net.minecraft.core.BlockPos;

import java.util.Set;

// Специальный интерфейс, для общения между всеми участниками сети
public interface IUniversalEnergy {
    RouterElectricalNet getNet();
    void setNet(RouterElectricalNet net);
    void updateNet(Set<BlockPos> ignorePos);
    BlockPos getPos();
}

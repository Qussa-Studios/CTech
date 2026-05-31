package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Electrical.ElectricalNet;
import QQussa.ctech.Electrical.IUniversalEnergy;
import QQussa.ctech.Electrical.RouterElectricalNet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RouterEntity extends BlockEntity implements IUniversalEnergy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterEntity.class);

    public RouterEntity(BlockPos pos, BlockState state) {
        super(BlockRegister.ROUTER_ENTITY.get(), pos, state);
    }

    // ==== Обязательные переменные ====

    public RouterElectricalNet net = null;

    // ==== Блок IUniversalEnergy ====

    @Override
    public BlockPos getPos() {
        return this.worldPosition;
    }

    //
    @Override
    public void updateNet(Set<BlockPos> ignorePos) {
        if (level.isClientSide) {
            return;
        }
        if (this.net == null) {
            LOGGER.debug("Сеть текущего роутера равно нулю, создается новая...");
            this.net = new RouterElectricalNet(this.level);
            final Set<BlockPos> me = new HashSet<>();
            me.add(this.worldPosition);
            this.net.addConnect(me);
            LOGGER.debug("Сеть текущего роутера обновлена, сеть " + this.net);
        }
        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();
        LOGGER.debug("Началась проверка области досягаемости");
        for (int startX = x - 7; startX <= x + 7; startX++) {
            for (int startY = y - 7; startY <= y + 7; startY++) {
                for (int startZ = z - 7; startZ <= z + 7; startZ++) {
                    BlockPos scanPos = new BlockPos(startX, startY, startZ);
                    LOGGER.debug("Проверяю " + startX + " " + startY + " " + startZ);
                    if (this.net.getConnectedBlock().contains(scanPos)) {
                        LOGGER.debug("Блок игнорируется");
                        LOGGER.debug("Skip...");
                        continue;
                    }
                    ignorePos.add(scanPos);

                    if (level.getBlockEntity(scanPos) instanceof IUniversalEnergy energy) {
                        LOGGER.debug("Блок с классом IUniversalEnergy найден!");
                        RouterElectricalNet scanNet = energy.getNet();
                        if (scanNet != null) {
                            LOGGER.debug("Нэт блока не ровна нулю, запускаю объединение сетей");
                            if (scanNet != this.net) {
                                this.net.merage(scanNet, this.worldPosition);
                                LOGGER.debug("Найден сосед с другой сетью на " + scanPos + ". Запуск слияния...");
                            }
                        } else {
                            LOGGER.debug("Нэт блока ровна нулю, присваиваю блоку нэт " + this.net);
                            energy.setNet(this.net);
                            final Set<BlockPos> newBlock = new HashSet<>();
                            newBlock.add(scanPos);
                            this.net.addConnect(newBlock);
                            energy.updateNet(ignorePos);
                        }
                    } else {
                        LOGGER.debug("Блок, содержащий IUniversalEnergy не найден");
                        LOGGER.debug("Skip...");
                    }
                }
            }
        }
    }

    // Получаем сеть этого блока
    @Override
    public RouterElectricalNet getNet() {
        return this.net;
    }

    // Задаем сеть этого блока
    @Override
    public void setNet(RouterElectricalNet net) {
        this.net = net;
    }

    // ==== Блок уникальных функций ===

    @Override
    public void onLoad() {
        final Set<BlockPos> ignorePos = new HashSet<>();
        ignorePos.add(this.worldPosition);
        updateNet(ignorePos);
        super.onLoad();
    }
}

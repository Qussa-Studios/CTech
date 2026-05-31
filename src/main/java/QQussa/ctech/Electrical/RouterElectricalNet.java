package QQussa.ctech.Electrical;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RouterElectricalNet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterElectricalNet.class);
    private final Set<BlockPos> connectedBlock = new HashSet<>(); // Список всех подключенных блоков
    private Level level; // Level для этой сети

    // Интерфейс для объявления новой сети
    public RouterElectricalNet(Level level) {
        this.level = level;
        LOGGER.info("Был объявлен нэт " + this);
    }

    // === Блок объединения сетей ===

    // Объединение сетей
    public void merage(RouterElectricalNet otherNet, BlockPos pPos) {
        Set<BlockPos> otherConnectedBlock = otherNet.getConnectedBlock();

        addConnect(otherConnectedBlock);
        LOGGER.debug("Началась слияние сетей с " + otherNet + "В коннекты этой сети были добавлены" + otherConnectedBlock);

        for (BlockPos pos : otherConnectedBlock) {
            if (this.level.getBlockEntity(pos) instanceof IUniversalEnergy energy) {

                energy.setNet(this);

                final Set<BlockPos> ignorePos = new HashSet<>();
                ignorePos.add(pPos);

                energy.updateNet(ignorePos);
                LOGGER.debug("Запрос на обновление был отправлен " + pos + " А так же его нэт был обновлен на " + this);
                continue;
            }
            LOGGER.debug("Ошибка в отправке запроса на обновление сети " + pos + " Нэт не был обновлен");
        }
        otherNet.forgetNet();
    }

    // Забыть сеть
    public void forgetNet() {
        this.connectedBlock.clear();
        LOGGER.debug("Нэт " + this + " забыт");
    }

    // Добавить в сеть
    public void addConnect(Set<BlockPos> connections) {
        this.connectedBlock.addAll(connections);
        LOGGER.debug("В нэт были добавлены" + connections);
    }
    // === Блок get ===

    public Set<BlockPos> getConnectedBlock() {
        LOGGER.debug("Запрос на получение коннектов");
        return this.connectedBlock;
    }
}

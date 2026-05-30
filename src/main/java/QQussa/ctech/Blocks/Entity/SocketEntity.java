package QQussa.ctech.Blocks.Entity;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Electrical.ElectricalNet;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class SocketEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();

    private ElectricalNet net;
    private final Set<BlockPos> connection = new HashSet<>();

    public void setNet(ElectricalNet net) {
        this.net = net;
    }

    public boolean addConnection(BlockPos neighborPos, Level level) {
        Set<BlockPos> conn = getConnection();
        if (conn.contains(neighborPos)) {
            LOGGER.info("§a[CTech] §eСоединение уже существует");
            return false;
        }
        this.connection.add(neighborPos);
        this.net = new ElectricalNet();
        LOGGER.info("§a[CTech] §eСоединение: §f" + neighborPos);

        if (level.getBlockEntity(neighborPos) instanceof SocketEntity entity) {
            ElectricalNet currentNet = this.net;
            ElectricalNet incomingNet = entity.getNet();

            if (currentNet.hashCode() > incomingNet.hashCode()) {
                currentNet.merage(entity.net,level);
            }
        }
        return true;
    }

    public Set<BlockPos> getConnection() {
        return this.connection;
    }

    public boolean inNet(BlockPos pos) {
        Set<BlockPos> conn = getConnection();
        if (conn.contains(pos)) {
            return true;
        }
        return false;
    }


    public ElectricalNet getNet() {
        return this.net;
    }

    public SocketEntity(BlockPos blockPos, BlockState state) {
        super(BlockRegister.SOCKET_ENTITY.get(), blockPos, state);
    }
}

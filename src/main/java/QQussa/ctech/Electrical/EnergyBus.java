package QQussa.ctech.Electrical;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * In this mod, the energy grid is implemented using a global energy state for the entire network,
 * rather than a "liquid energy" (fluid-like transport) philosophy.
 * <p>The {@code EnergyBus} class serves as the controller and the "brain" of the energy network.</p>
 * <p><b>Important Requirement:</b> Every block connected to this network <b>must implement</b>
 * the {@link IUniversalEnergy} interface. Without it, the bus cannot register, track, or manage the block.</p>
 *
 * <p>It stores and manages:</p>
 * <ul>
 *     <li>The total amount of {@code EP} and {@code EF} available to other machines;</li>
 *     <li>The world position of every block connected to this network;</li>
 *     <li>The overall management and distribution of energy within the grid.</li>
 * </ul>
 * <p>
 * To initialize a new network, it is recommended to use the factory method {@link #create(Level, GlobalPos)}.
 * <p><b>Warning:</p></b> This method may return {@code null}. Always handle the result properly to avoid a {@code NullPointerException} (NPE).
 * </p>
 * <pre>{@code
 * private EnergyBus bus;
 *
 * // Initialize a new energy bus
 * EnergyBus.create(pLevel, pGPos);
 * }</pre>
 */
public class EnergyBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyBus.class);
    private Map<eType, Set<GlobalPos>> connections;

    private EnergyBus() {
        this.connections = new HashMap<>();
    }

    /**
     * The {@code .create()} method acts as a factory initializer for a new energy network (EnergyBus).
     * It ensures the grid is set up correctly, and simplifies future updates if the network creation logic changes.
     * <p>
     * Note: This method handles the creation internally and returns a boolean status.
     * </p>
     * <pre><code class="java">
     * // Initialize a new energy bus and check if it succeeded
     * if (EnergyBus.create(pLevel, pGPos)) {
     *     // Network was successfully created and the root block attached
     * }
     * </code></pre>
     *
     * @param pLevel The world level where the network is being created.
     * @param pGPos  The global position of the starting (root) block.
     * @return {@code true} if the EnergyBus was successfully created and the root block connected;
     *         {@code false} if the initialization failed.
     */
    public static boolean create(Level pLevel, GlobalPos pGPos) {
        EnergyBus newBus = new EnergyBus();
        if (!newBus.connect(pLevel, pGPos)) {
            LOGGER.debug("[create] fail attempt creating new NET for {}", pGPos.pos());
            return false;
        }
        return true;
    }

    /**
     * The {@code connect} method is used to attach a block to the energy bus.
     * <pre>{@code
     *
     *  private EnergyBus bus;
     *
     *  if (bus != nul) {
     *      bus.connect(pLevel,pGPos);
     *  }
     *  }</pre>
     *
     * @param pLevel The world level of the machine being connected.
     * @param pGPos  The global position of the machine being connected.
     * @return {@code true} if the connection was established successfully; {@code false} otherwise.
     */
    public boolean connect(Level pLevel, GlobalPos pGPos) {
        if (pLevel == null || pGPos == null) {
            LOGGER.debug("[addConnections] pLevel {}, or pGPos {} is null", pLevel, pGPos);
            return false;
        }

        if (pLevel.isClientSide) {
            return false;
        }

        if (pLevel.getBlockEntity(pGPos.pos()) instanceof IUniversalEnergy iue) {
            this.connections.computeIfAbsent(iue.getEnergyType(), k -> new HashSet<>()).add(pGPos);
            if (iue.setNet(this)) {
                LOGGER.debug("[connect] Successful add {} Block", pGPos.pos());
                return true;
            }
            forget(pLevel, pGPos);
            LOGGER.debug("[connect] Error on add {} Block, Block rejet connect invite", pGPos.pos());
            return false;
        }
        LOGGER.debug("[connect] Error on add {} Block, block not have IUniversalEnergy", pGPos.pos());
        return false;
    }

    /**
     * The {@code forget} method is used to detach a block from the energy bus.
     *
     * <pre>{@code
     *  EnergyBus bus;
     *
     *  if (bus != null) {
     *     bus.forget(pLevel, pGPos);
     *  }
     * }</pre>
     * @param pLevel The world level of the machine being disconnected.
     * @param pGPos  The global position of the machine being disconnected.
     * @return {@code true} if the block was successfully disconnected; {@code false} otherwise.
     */
    public boolean forget(Level pLevel, GlobalPos pGPos) {
        if (pLevel == null || pGPos == null) {
            LOGGER.debug("[forget] pLevel {}, or pGPos {} is null", pLevel, pGPos);
            return false;
        }
        if (pLevel.isClientSide) {
            return false;
        }
        if (pLevel.getBlockEntity(pGPos.pos()) instanceof IUniversalEnergy iue) {
            Set<GlobalPos> setByType = getConnections().get(iue.getEnergyType());
            if (setByType == null) {
                LOGGER.debug("[forget] in connections not have {} eType", iue.getEnergyType());
                return false;
            }
            if (iue.getNet() == null || iue.getNet() != this) {
                LOGGER.debug("[forget] Attempt to forget network for {}, but block's network does not match this network", pGPos.pos());
                setByType.remove(pGPos);
                return true;
            }
            setByType.remove(pGPos);
            iue.setNet(null);
            return true;
        }
        LOGGER.debug("[forget] block {} not have IUniversalEnergy", pGPos.pos());
        return false;
    }

    /**
     * The {@code getConnections} method returns a map of all established connections.
     * <p>
     * Warning: During development, please note that this method returns a {@literal Map<eType, Set<GlobalPos>>}.
     * </p>
     *
     * <pre>{@code
     * Map<eType, Set<GlobalPos>> allControllers = getConnections().get(eType.NET_CONTROLLER);
     *
     * }</pre>
     *&lt;
     * @return A {@code Map<eType, Set<GlobalPos>>} containing all registered connections grouped by energy type.
     */
    public Map<eType, Set<GlobalPos>> getConnections() {
        return this.connections;
    }
}

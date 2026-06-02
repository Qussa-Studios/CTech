package QQussa.ctech.Electrical;

// Специальный интерфейс, для общения между всеми участниками сети
public interface IUniversalEnergy {
    EnergyBus getNet(); // return block EnergyBus

    boolean setNet(EnergyBus net); // set block EnergyBus

    eType getEnergyType(); // return block eType
}

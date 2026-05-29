package QQussa.ctech.Blocks;

import QQussa.ctech.Blocks.Class.ZettaClass;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);

    public static final RegistryObject<ZettaClass> ZETTA = BLOCK.register("zetta",() -> new ZettaClass(BlockBehaviour.Properties.of().strength(1f)));

    public static void registry(IEventBus eventBus) {
        BLOCK.register(eventBus);
    }
}

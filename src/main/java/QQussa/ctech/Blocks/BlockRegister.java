package QQussa.ctech.Blocks;

import QQussa.ctech.Blocks.Class.NETController;
import QQussa.ctech.Blocks.Entity.NETControllerEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class BlockRegister {

    //
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);

    // -- ALL BLOCK
    public static final RegistryObject<Block> NET_CONTROLLER = BLOCK.register("net_controller",()-> new NETController(BlockBehaviour.Properties.of()));
    // -- ALL BLOCK ENTITY
    public static final RegistryObject<BlockEntityType<NETControllerEntity>> NET_CONTROLLER_ENTITY = BLOCK_ENTITY.register("net_controller",
            ()-> BlockEntityType.Builder.of(NETControllerEntity::new,NET_CONTROLLER.get()).build(null));

    //
    public static void registry(IEventBus eventBus) {
        BLOCK.register(eventBus);
        BLOCK_ENTITY.register(eventBus);
    }
}

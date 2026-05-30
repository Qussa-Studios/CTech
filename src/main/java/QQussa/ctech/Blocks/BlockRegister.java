package QQussa.ctech.Blocks;

import QQussa.ctech.Blocks.Class.WireClass;
import QQussa.ctech.Blocks.Entity.WireEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class BlockRegister {

    // Регистрация блоков и сущностей блоков
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);

    public static final RegistryObject<Block> WIRE = BLOCK.register("wire",() -> new WireClass(BlockBehaviour.Properties.of()));
    public static final RegistryObject<BlockEntityType<WireEntity>> WIRE_ENTITY = BLOCK_ENTITY.register("wire_entity"
            ,() -> BlockEntityType.Builder.of(WireEntity::new,BlockRegister.WIRE.get()).build(null));

    // Регистрация в шине
    public static void registry(IEventBus eventBus) {
        BLOCK.register(eventBus);
        BLOCK_ENTITY.register(eventBus);
    }
}

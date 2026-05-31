package QQussa.ctech.Blocks;

import QQussa.ctech.Blocks.Class.CreativeAccumulatorClass;
import QQussa.ctech.Blocks.Class.EnergyBlackHoleClass;
import QQussa.ctech.Blocks.Class.RouterClass;
import QQussa.ctech.Blocks.Class.WireClass;
import QQussa.ctech.Blocks.Entity.CreativeAccumulatorEntity;
import QQussa.ctech.Blocks.Entity.EnergyBlackHoleEntity;
import QQussa.ctech.Blocks.Entity.RouterEntity;
import QQussa.ctech.Blocks.Entity.WireEntity;
import net.minecraft.world.level.block.Block;
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
    public static final RegistryObject<Block> CREATIVE_ACCUMULATOR = BLOCK.register("creative_accumulator",()->new CreativeAccumulatorClass(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ENERGY_BLACK_HOLE = BLOCK.register("e_black_hole",()->new EnergyBlackHoleClass(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ROUTER = BLOCK.register("router_t1",()->new RouterClass(BlockBehaviour.Properties.of()));

    public static final RegistryObject<BlockEntityType<WireEntity>> WIRE_ENTITY = BLOCK_ENTITY.register("wire_entity"
            ,() -> BlockEntityType.Builder.of(WireEntity::new,BlockRegister.WIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CreativeAccumulatorEntity>> CREATIVE_ACCUMULATOR_ENTITY = BLOCK_ENTITY.register("creative_accumulator_entity"
            ,() -> BlockEntityType.Builder.of(CreativeAccumulatorEntity::new,BlockRegister.CREATIVE_ACCUMULATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyBlackHoleEntity>> ENERGY_BLACK_HOLE_ENTITY = BLOCK_ENTITY.register("e_black_hole_entity"
            ,() -> BlockEntityType.Builder.of(EnergyBlackHoleEntity::new,BlockRegister.ENERGY_BLACK_HOLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<RouterEntity>> ROUTER_ENTITY = BLOCK_ENTITY.register("router"
            ,()-> BlockEntityType.Builder.of(RouterEntity::new,BlockRegister.ROUTER.get()).build(null));
    // Регистрация в шине
    public static void registry(IEventBus eventBus) {
        BLOCK.register(eventBus);
        BLOCK_ENTITY.register(eventBus);
    }
}

package QQussa.ctech.Item;

import QQussa.ctech.Blocks.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);

    public static final RegistryObject<BlockItem> WIRE_ITEM = ITEM.register("wire",() -> new BlockItem(BlockRegister.WIRE.get(),new Item.Properties()));

    public static void registry(IEventBus eventBus) {
        ITEM.register(eventBus);
    }
}

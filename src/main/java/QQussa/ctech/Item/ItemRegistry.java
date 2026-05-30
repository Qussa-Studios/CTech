package QQussa.ctech.Item;

import QQussa.ctech.Blocks.BlockRegister;
import QQussa.ctech.Item.Class.CoilClass;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);

    public static final RegistryObject<BlockItem> SOCKET_ITEM = ITEM.register("socket",() -> new BlockItem(BlockRegister.SOCKET.get(),new Item.Properties()));
    public static final RegistryObject<BlockItem> WIRE_ITEM = ITEM.register("wire",() -> new BlockItem(BlockRegister.WIRE.get(),new Item.Properties()));
    public static final RegistryObject<Item> COIL_ITEM = ITEM.register("coil",() -> new CoilClass(new Item.Properties().stacksTo(1)));
    public static void registry(IEventBus eventBus) {
        ITEM.register(eventBus);
    }
}

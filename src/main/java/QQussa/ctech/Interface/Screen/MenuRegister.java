package QQussa.ctech.Interface.Screen;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class MenuRegister {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(ForgeRegistries.MENU_TYPES,MODID);

    public static final RegistryObject<MenuType<ZettaMenu>> ZETTA_MENU = MENU.register("zetta_menu", () -> IForgeMenuType.create(((windowId, inv, data) -> new ZettaMenu(windowId,inv))));

    public static void register(IEventBus eventBus) {
        MENU.register(eventBus);
    }
}

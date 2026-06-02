package QQussa.ctech.CreativeTab;

import QQussa.ctech.Item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static QQussa.ctech.Ctech.MODID;

public class CreativeTabRegister {
    // Регистрация вкладки
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);

    public static final RegistryObject<CreativeModeTab> CTECH_ENERGY = CREATIVE_TABS.register("ctec_energy",()->
            CreativeModeTab.builder()
                    .icon(()->new ItemStack(ItemRegister.NET_CONTROLLER_ITEM.get()))
                    .title(Component.translatable("creativetab.ctech.energy"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegister.NET_CONTROLLER_ITEM.get());
                    })
                    .build()
    );

    public static void registry(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}

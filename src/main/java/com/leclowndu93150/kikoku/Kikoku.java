package com.leclowndu93150.kikoku;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Kikoku.MODID)
public class Kikoku {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "kikoku";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryObject<Item> Kikoku = register("kikoku", () -> new KikokuItem(Materials.KIKOKU,  13, -2.4F, (new Item.Properties())));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> KIKOKU_TAB = CREATIVE_MODE_TABS.register("kikoku_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> Kikoku.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            output.accept(Kikoku.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_MODE_TABS.register(modEventBus);
        ModRegistry.register();
    }

    public static RegistryObject<Item> register(final String name, final Supplier<Item> sup) {
        RegistryObject<Item> item = ModRegistry.ITEMS.register(name, sup);
        CreativeTab.setTab(item::get, BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.TOOLS_AND_UTILITIES));
        return item;
    }

}

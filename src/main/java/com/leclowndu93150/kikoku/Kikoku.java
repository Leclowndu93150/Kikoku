package com.leclowndu93150.kikoku;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.leclowndu93150.kikoku.ModRegistry.ATTRIBUTES;
import static com.leclowndu93150.kikoku.ModRegistry.ITEMS;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Kikoku.MODID)
public class Kikoku {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "kikoku";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> KIKOKU_TAB = CREATIVE_MODE_TABS.register("kikoku_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemList.Kikoku.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            output.accept(ItemList.Kikoku.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());



        public Kikoku() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(CreativeTab::buildContents);
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            EVENT_BUS.register(this);
            CREATIVE_MODE_TABS.register(modEventBus);
            ITEMS.register(modEventBus);
            ATTRIBUTES.register(modEventBus);
            Config.register();
        }

}



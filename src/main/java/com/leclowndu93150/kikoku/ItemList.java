package com.leclowndu93150.kikoku;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemList {
    public static final RegistryObject<Item> Kikoku = register("kikoku", () ->
            new KikokuItem(Materials.KIKOKU,  13, -2.4F, (new Item.Properties())));
    public static void register() {}

    public static RegistryObject<Item> register(final String name, final Supplier<Item> sup) {
        RegistryObject<Item> item = ModRegistry.ITEMS.register(name, sup);
        CreativeTab.setTab(item::get, BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.TOOLS_AND_UTILITIES));

        return item;
    }
}
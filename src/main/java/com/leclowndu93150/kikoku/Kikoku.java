package com.leclowndu93150.kikoku;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.List;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(Kikoku.MODID)
public class Kikoku {

    public static final String MODID = "kikoku";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Custom tool tier definition
    public static final Tier KIKOKU = TierSortingRegistry.registerTier(
            new ForgeTier(5, 0, 0, 16, 250, Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(Items.DIRT)),
            new ResourceLocation("kikoku:kikoku"),
            List.of(Tiers.NETHERITE),
            List.of()
    );

    // Register items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> Kikoku = ITEMS.register("kikoku", () ->
            new KikokuItem(KIKOKU, 13, -2.4F, (new Item.Properties())));

    // Register attributes
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
    public static final RegistryObject<Attribute> DivineDamage = ATTRIBUTES.register("divine_damage", () ->
            new RangedAttribute("attribute.kikoku.divinedamage", 0, 0, Integer.MAX_VALUE));
    public static final RegistryObject<Attribute> ArmorPiercingDamage = ATTRIBUTES.register("armor_piercing_damage", () ->
            new RangedAttribute("attribute.kikoku.armorpiercingdamage", 0, 0, Integer.MAX_VALUE));
    public static final RegistryObject<Attribute> SoulDamage = ATTRIBUTES.register("soul_damage", () ->
            new RangedAttribute("attribute.kikoku.souldamage", 0, 0, Integer.MAX_VALUE));

    // Creative mode tab registration
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> KIKOKU_TAB = CREATIVE_MODE_TABS.register("kikoku_tab", () ->
            CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.literal("Kikoku"))
                    .icon(() -> Kikoku.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(Kikoku.get());
                    }).build()
    );

    public Kikoku() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        Config.register();
    }
}

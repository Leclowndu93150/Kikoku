package com.leclowndu93150.kikoku;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class Materials {
    public static final TagKey<Block> KIKOKU_TAG = ModTags.Blocks.mod("needs_kikoku_tool");
    public static final Tier KIKOKU = TierSortingRegistry.registerTier(new ForgeTier(5, 0, 0, 16, 250, KIKOKU_TAG, () -> Ingredient.of(Items.DIRT)),
            new ResourceLocation("kikoku:kikoku"),
            List.of(Tiers.NETHERITE), List.of());
}
package com.leclowndu93150.kikoku;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final class Blocks {

        public static TagKey<Block> forge(String path) {
            return BlockTags.create(new ResourceLocation("forge", path));
        }

        public static TagKey<Block> mod(String path) {
            return BlockTags.create(new ResourceLocation(Kikoku.MODID, path));
        }
    }

    }


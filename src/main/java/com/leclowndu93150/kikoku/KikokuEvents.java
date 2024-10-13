package com.leclowndu93150.kikoku;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Kikoku.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KikokuEvents {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack sword = event.getLeft();
        ItemStack book = event.getRight();

        if (!(sword.getItem() instanceof KikokuItem) || book.getItem() != Items.ENCHANTED_BOOK) {
            return;
        }

        Map<Enchantment, Integer> swordMap = EnchantmentHelper.getEnchantments(sword);
        Map<Enchantment, Integer> bookMap = EnchantmentHelper.getEnchantments(book);

        if (bookMap.isEmpty()) return;

        Map<Enchantment, Integer> outputMap = new HashMap<>(swordMap);
        int costCounter = 0;

        // Merge enchantments from the book into the sword
        for (Map.Entry<Enchantment, Integer> entry : bookMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int bookLevel = entry.getValue();
            int swordLevel = swordMap.getOrDefault(enchantment, 0);
            int newLevel = swordLevel + bookLevel;

            outputMap.put(enchantment, newLevel);
            costCounter += 2 + (newLevel * 5);  // Adjust cost calculation
        }

        event.setCost(costCounter);
        ItemStack enchantedSword = sword.copy();
        EnchantmentHelper.setEnchantments(outputMap, enchantedSword);
        event.setOutput(enchantedSword);
    }


    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide && event.getOutput().getItem() instanceof KikokuItem) {
            if (event.getEntity() instanceof AbstractClientPlayer) {
                event.getEntity().playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            }
        }
    }
}

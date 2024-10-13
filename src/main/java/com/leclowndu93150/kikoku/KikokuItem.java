package com.leclowndu93150.kikoku;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.leclowndu93150.kikoku.Kikoku.*;

public class KikokuItem extends SwordItem {

    public static final UUID SOUL_DAMAGE_MODIFIER = UUID.fromString("d2928c01-5d7d-41c5-bd3a-9ca8f43c8ff8");
    public static final ResourceKey<DamageType> DIVINE_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MODID, "divine_damage"));
    public static final ResourceKey<DamageType> ARMOR_PIERCING_DAMAGE_SOURCE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MODID, "armor_piercing_damage"));

    public KikokuItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(equipmentSlot);
        ListMultimap<Attribute, AttributeModifier> multimaps = ArrayListMultimap.create();

        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            multimaps.put(ArmorPiercingDamage.get(), new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Armor Piercing Damage Modifier", 3, AttributeModifier.Operation.ADDITION));
            multimaps.put(DivineDamage.get(), new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Divine Damage Modifier", 1, AttributeModifier.Operation.ADDITION));
            multimaps.put(SoulDamage.get(), new AttributeModifier(SOUL_DAMAGE_MODIFIER, "Soul Damage Modifier", 0.25, AttributeModifier.Operation.ADDITION));
        }

        multimap.forEach((attribute, modifier) -> multimaps.put(attribute, modifier));
        return multimaps;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target == null || !target.isAttackable() || attacker.level().isClientSide) {
            return false;
        }
        Map<Enchantment, Integer> stackEnchantments = EnchantmentHelper.getEnchantments(stack);
        int sharpnessLevel = stackEnchantments.getOrDefault(Enchantments.SHARPNESS, 0);

        // Adjust damage calculation based on the sharpness level
        float divineDamage = (sharpnessLevel * 0.5F) + 2.5F;
        float armorPiercingDamage = (sharpnessLevel * 0.5F) + 4.5F;

        // If the target is a player in creative mode
        if (target instanceof Player player) {
            if (player.isCreative()) {
                target.invulnerableTime = 0;
                target.hurt(player.level().damageSources().source(DIVINE_DAMAGE_TYPE), divineDamage);
            }
        }

        target.invulnerableTime = 0;
        target.hurt(target.level().damageSources().source(ARMOR_PIERCING_DAMAGE_SOURCE), armorPiercingDamage);
        drainHealth(target);
        return true;
    }

    private void drainHealth(LivingEntity target) {
        AttributeModifier soulModifier = target.getAttribute(Attributes.MAX_HEALTH).getModifier(SOUL_DAMAGE_MODIFIER);
        double newAmount = (soulModifier == null) ? -0.25 : soulModifier.getAmount() - 0.25;

        // Update the maximum health modifier
        if (soulModifier != null) {
            target.getAttribute(Attributes.MAX_HEALTH).removePermanentModifier(SOUL_DAMAGE_MODIFIER);
        }

        target.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(SOUL_DAMAGE_MODIFIER, "Soul Damage", newAmount, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1; // The item cannot stack
    }


}

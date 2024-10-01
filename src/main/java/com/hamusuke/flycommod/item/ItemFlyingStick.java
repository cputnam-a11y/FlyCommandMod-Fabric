package com.hamusuke.flycommod.item;

import com.hamusuke.flycommod.invoker.LivingEntityInvoker;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class ItemFlyingStick extends Item {
	public ItemFlyingStick() {
		super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true));
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable(this.getTranslationKey() + ".desc"));
		super.appendTooltip(stack, context, tooltip, type);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerIn, Hand handIn) {
		ItemStack item = playerIn.getStackInHand(handIn);
		if (!playerIn.getAbilities().allowFlying) {
			playerIn.getAbilities().allowFlying = true;
			playerIn.sendAbilitiesUpdate();
		} else {
			playerIn.getAbilities().allowFlying = false;
			playerIn.getAbilities().flying = false;
			playerIn.sendAbilitiesUpdate();
			((LivingEntityInvoker) playerIn).flyCommandMod_Fabric$markNoFallDamage(!playerIn.isOnGround());
		}
		return TypedActionResult.success(item);
	}

}

package com.hamusuke.flycommod;

import com.hamusuke.flycommod.command.CommandEntityAbilities;
import com.hamusuke.flycommod.command.CommandFlying;
import com.hamusuke.flycommod.item.ItemFlyingStick;
import com.hamusuke.flycommod.network.NetworkManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FlyCommandMod implements ModInitializer {
	public static final String MOD_ID = "flycommand";

	public static final Item FLYING_STICK = new ItemFlyingStick();

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "flying_stick"), FLYING_STICK);
		NetworkManager.init();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> {
			CommandFlying.register(dispatcher);
			CommandEntityAbilities.register(dispatcher);
		});

		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> player.sendAbilitiesUpdate());
	}
}

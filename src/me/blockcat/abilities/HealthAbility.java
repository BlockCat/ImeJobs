package me.blockcat.abilities;

import java.util.List;

import me.blockcat.ImeJobs;
import me.blockcat.InventoryString;
import me.blockcat.SlotItem;
import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class HealthAbility extends Ability {

	@Override
	public void initiate() {

	}
	
	@ImeEvent(event = InventoryCloseEvent.class)
	public void onInventoryClose (Wrapper w) {
		InventoryCloseEvent event = (InventoryCloseEvent) w.getEvent();
		List<SlotItem> slots = InventoryString.getSlotItemsFromString(ImeJobs.getPlugin().playerData.getData(event.getPlayer().getName()).getInventoryString());

		int count = 0;

		for (SlotItem si : slots) {
			List<AbilityShop> abilities = si.getAbilities();
			if (!abilities.contains(AbilityShop.HEALTH)) {
				continue;
			}

			for (AbilityShop as : abilities) {
				if (as.equals(AbilityShop.HEALTH)) {
					count++;
				}
			}
		}
		event.getPlayer().setMaxHealth(20 + (2 * count));
	}
}

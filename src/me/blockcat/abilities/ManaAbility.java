package me.blockcat.abilities;

import java.util.List;

import me.blockcat.ImeJobs;
import me.blockcat.InventoryString;
import me.blockcat.SlotItem;
import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ManaAbility extends Ability {

	public ImeJobs plugin;
	public ManaAbility(ImeJobs plugin) {
		this.plugin = plugin;
	}
	
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
			if (!abilities.contains(AbilityShop.MANA)) {
				continue;
			}

			for (AbilityShop as : abilities) {
				if (as.equals(AbilityShop.MANA)) {
					count++;
				}
			}
		}
		int playerMana = event.getPlayer().getMetadata("fwMana").get(0).asInt();
		event.getPlayer().setMetadata("fwMana", new FixedMetadataValue(plugin, playerMana + count));
	}

}

package me.blockcat.abilities;

import java.util.List;

import me.blockcat.ImeJobs;
import me.blockcat.InventoryString;
import me.blockcat.SlotItem;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.entity.Player;

public abstract class Ability {
	
	public Ability() {
		
	}
	
	public abstract void initiate();
	
	protected int countAbility(Player player, AbilityShop ability) {
		List<SlotItem> slots = InventoryString.getSlotItemsFromString(ImeJobs.getPlugin().playerData.getData(player.getName()).getInventoryString());

		int count = 0;

		for (SlotItem si : slots) {
			List<AbilityShop> abilities = si.getAbilities();
			if (!abilities.contains(ability)) {
				continue;
			}

			for (AbilityShop as : abilities) {
				if (as.equals(ability)) {
					count++;
				}
			}
		}
		return count;
	}

}

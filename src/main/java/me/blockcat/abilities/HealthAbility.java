package me.blockcat.abilities;

import me.blockcat.ImeJobs;
import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealthAbility extends Ability {

	@Override
	public void initiate() {

	}
	
	@ImeEvent(event = InventoryCloseEvent.class)
	public void onInventoryClose (Wrapper w) {
		InventoryCloseEvent event = (InventoryCloseEvent) w.getEvent();
		
		int count = this.countAbility((Player) event.getPlayer(), AbilityShop.HEALTH);
		
		event.getPlayer().setMaxHealth(20 + (2 * count));
	}
	
	@ImeEvent(event = PlayerRespawnEvent.class)
	public void onPlayerRespawn (Wrapper w) {
		PlayerRespawnEvent event = (PlayerRespawnEvent) w.getEvent();
		final Player player = event.getPlayer();
		
		final int count = this.countAbility(event.getPlayer(), AbilityShop.HEALTH);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(ImeJobs.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (count > 0) {
					player.setMaxHealth(20 + (2 * count));
					player.setHealth(20 + (2 * count));
				}
			}
			
		}, 10L);
	}
}

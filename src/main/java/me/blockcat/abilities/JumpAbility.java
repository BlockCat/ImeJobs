package me.blockcat.abilities;

import me.blockcat.ImeJobs;
import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpAbility extends Ability {

	@Override
	public void initiate() {
		// TODO Auto-generated method stub
	}

	@ImeEvent (event=InventoryCloseEvent.class)
	public void onInventoryClose(Wrapper w) {
		InventoryCloseEvent event = (InventoryCloseEvent) w.getEvent();

		int count = this.countAbility((Player)event.getPlayer(), AbilityShop.JUMP);
		if (count > 0) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 960000 ,count), true);
		} else {
			event.getPlayer().removePotionEffect(PotionEffectType.JUMP);
		}
	}
	
	@ImeEvent (event=PlayerItemConsumeEvent.class)
	public void onItemConsume (Wrapper w) {
		PlayerItemConsumeEvent event = (PlayerItemConsumeEvent) w.getEvent();
		if (event.getItem().getType() == Material.MILK_BUCKET) {
			int count = this.countAbility((Player)event.getPlayer(), AbilityShop.JUMP);
			if (count > 0) {
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 960000 ,count), true);
			}
		}
	}
	
	@ImeEvent(event = PlayerRespawnEvent.class)
	public void onPlayerRespawn (Wrapper w) {
		PlayerRespawnEvent event = (PlayerRespawnEvent) w.getEvent();
		final int count = this.countAbility((Player)event.getPlayer(), AbilityShop.JUMP);
		
		final Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(ImeJobs.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (count > 0) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 960000 ,count), true);
				}
			}
			
		}, 10L);
		
	}
}


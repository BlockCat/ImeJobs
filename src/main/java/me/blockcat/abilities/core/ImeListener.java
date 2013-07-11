package me.blockcat.abilities.core;

import me.blockcat.ImeJobs;
import me.blockcat.events.ItemSlateEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class ImeListener implements Listener{


	public ImeListener() {
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		ImeJobs.abHandler.executeEvent(event);
	}
	
	@EventHandler
	public void onInventoryClose (InventoryCloseEvent event) {
		ImeJobs.abHandler.executeEvent(event);
	}
	
	@EventHandler 
	public void onItemSlate (ItemSlateEvent event) {
		ImeJobs.abHandler.executeEvent(event);
	}
	
	@EventHandler
	public void onPlayerLogin (PlayerLoginEvent event) {
		ImeJobs.abHandler.executeEvent(event);
	}
}

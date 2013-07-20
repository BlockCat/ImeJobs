package me.blockcat.abilities;

import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BerserkAbility extends Ability {
	/**
	 * This ability let players do 1 more damage.
	 * 
	 */
	@Override
	public void initiate() {
		
	}
	
	@ImeEvent(event = EntityDamageByEntityEvent.class)
	public void onEntityDamagedByEntity(Wrapper w) {
		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) w.getEvent();
		
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getDamager();
		int count = this.countAbility(player, AbilityShop.BERSERK);
		event.setDamage(event.getDamage() + count);
	}	

}

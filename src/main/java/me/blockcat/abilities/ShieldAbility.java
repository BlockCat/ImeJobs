package me.blockcat.abilities;

import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShieldAbility extends Ability {

	@Override
	public void initiate() {
		// TODO Auto-generated method stub
		
	}
	
	@ImeEvent(event = EntityDamageByEntityEvent.class)
	public void onEntityDamagedByEntity(Wrapper w) {
		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) w.getEvent();
		
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		int count = this.countAbility(player, AbilityShop.SHIELD);
		
		event.setDamage((event.getDamage() - count > 0) ? event.getDamage() - count : 0.1);		
	}

}

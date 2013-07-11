package me.blockcat.abilities.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;

import me.blockcat.abilities.Ability;
import me.blockcat.abilities.HealthAbility;
import me.blockcat.abilities.ManaAbility;
import me.blockcat.abilities.anotation.ImeEvent;
import me.blockcat.abilities.anotation.Wrapper;

import org.bukkit.event.Event;

public class AbilityHandler {
	
	public HashMap<Class<? extends Event>, HashMap<Ability, Method>> events = new HashMap<Class<? extends Event>, HashMap<Ability, Method>>();
	
	public AbilityHandler () {
	}
	
	public void addClasses() throws InstantiationException, IllegalAccessException {
		for (AbilityShop a : AbilityShop.values()) {
			this.addClass(a.getHandler().newInstance());
		}
	}
	
	public void addClass(Ability ability) {
		for (Method m : ability.getClass().getDeclaredMethods()) {
			if (m.isAnnotationPresent(ImeEvent.class)) {
				ImeEvent im = m.getAnnotation(ImeEvent.class);
				if (events.containsKey(im.event())) {
					HashMap<Ability, Method> m1 = events.get(im.event());
					m1.put(ability, m);
					events.put(im.event(), m1);
				} else {
					HashMap<Ability, Method> m1 = new HashMap<Ability, Method>();
					m1.put(ability, m);
					events.put(im.event(), m1);
				}
			}
		}
	}
	
	public void executeEvent (Event event) {
		if (!events.containsKey(event.getClass())) return;
		Wrapper wrapper = new Wrapper(event);
		for (Entry <Ability, Method> ent : events.get(event.getClass()).entrySet()) {
			try {
				ent.getValue().invoke(ent.getKey(), wrapper);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public enum AbilityShop {
		HEALTH(HealthAbility.class), 
		MANA(ManaAbility.class);
		
		private Class<? extends Ability> handler;
		
		AbilityShop(Class<? extends Ability> handler) {
			this.handler = handler;
		}
		
		public Class<? extends Ability> getHandler() {
			return handler;
		}
	}
}

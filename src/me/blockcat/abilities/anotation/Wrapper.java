package me.blockcat.abilities.anotation;

import org.bukkit.event.Event;

public class Wrapper {
	
	private Event event = null;
	private boolean isCancelled = false;
	
	public Wrapper (Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * @param isCancelled the isCancelled to set
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	

}

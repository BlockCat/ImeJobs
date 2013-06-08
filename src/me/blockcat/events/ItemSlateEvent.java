package me.blockcat.events;

import java.util.List;

import me.blockcat.SlotItem;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemSlateEvent extends Event {
	
	private static HandlerList handlerList = new HandlerList();
	private Click click;
	private SlotItem slotItem;
	private List<SlotItem> activeSlots;
	private Player player;
	
	
	public ItemSlateEvent(Player player, Click click, SlotItem slotItem, List<SlotItem> activeSlots) {
		this.player = player;
		this.click = click;
		this.slotItem = slotItem;
		this.activeSlots = activeSlots;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Click getClick() {
		return click;
	}
	
	public SlotItem getSlotItem() {
		return slotItem;
	}
	/**
	 * 
	 * @return List<SlotItem> returns the current slotItems held in the inventory
	 */
	public List<SlotItem> getActiveSlots() {
		return activeSlots;
	}
	
	public static HandlerList getHandlerList() {
        return handlerList;
    }
	
	public enum Click {
		ADD, REMOVE
	}

}

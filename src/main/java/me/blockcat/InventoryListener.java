package me.blockcat;

import java.util.ArrayList;
import java.util.List;

import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;
import me.blockcat.events.ItemSlateEvent;
import me.blockcat.events.ItemSlateEvent.Click;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryOpen (InventoryOpenEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase("Level-slate: " + event.getPlayer().getName())) return;

		int maxSlots = 6;
		try {
			Quester quester = ((Quests)Bukkit.getPluginManager().getPlugin("Quests")).getQuester(event.getPlayer().getName());
			int qp = quester.getBaseData().getInt("quest-points");
			maxSlots = 6 + (int)(qp/10);
			int sl = Integer.parseInt(ImeJobs.getPlugin().playerData.getData(event.getPlayer().getName()).getEntry("maxSlots"));
			maxSlots = (maxSlots >= 54) ? 54 : maxSlots;
			if (maxSlots > sl) {
				//got slots added!
			} else if (maxSlots < sl) {

			}
			ImeJobs.getPlugin().playerData.getData(event.getPlayer().getName()).setEntry("maxSlots", maxSlots + "");
		} catch(Exception e) {
			maxSlots = Integer.parseInt(ImeJobs.getPlugin().playerData.getData(event.getPlayer().getName()).getEntry("maxSlots"));
		}


		//a slotitem is stored in: @id:data:slot#name

		//configure items
		Inventory inv = event.getInventory();
		//fill blocks
		ItemStack fillItem = new ItemStack(Material.OBSIDIAN);
		ItemMeta meta = fillItem.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Not unlocked");
		fillItem.setItemMeta(meta);

		for (int i = 53; i >= maxSlots; i--) {
			inv.setItem(i, fillItem);
		}
	}

	/*
	 * Theory:
	 * if item on cursor is null, and rawslot is < 53, then there is something placed in the slate.
	 * 
	 */
	@EventHandler
	public void onInventoryClick (InventoryClickEvent event) {
		//check if it's a level-slate
		if (!event.getInventory().getName().equalsIgnoreCase("Level-slate: " + event.getWhoClicked().getName())) return;
		Player player = (Player) event.getWhoClicked();

		int maxSlots = Integer.parseInt(ImeJobs.getPlugin().playerData.getData(player.getName()).getEntry("maxSlots"));

		//check if there is clicked on an open slot.
		if (!canClick(event.getRawSlot(), maxSlots)) {
			event.setCancelled(true);
			return;
		}

		SlotItem si = null;

		//check if the player has the requirements...
		List<SlotItem> slotItems = new ArrayList<SlotItem>();
		for (int i = 0; i < maxSlots; i++) {

			ItemStack itemCheck = event.getInventory().getItem(i);
			if (itemCheck == null) continue;
			SlotItem isCheck = SlotItem.getFromStack(itemCheck);
			if (isCheck == null) continue;

			slotItems.add(isCheck);
		}

		//is shift click
		if (event.isShiftClick()) {
			si = SlotItem.getFromStack(event.getCurrentItem());

			if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if (si == null) return;
				if (event.getRawSlot() < 54) {

					//remove slot
					ItemSlateEvent customEvent = new ItemSlateEvent(player, Click.REMOVE, si, slotItems);
					Bukkit.getServer().getPluginManager().callEvent(customEvent);

					si.removePermission(player);
				} else {

					//all slotItems are in the list, now check.
					if (!si.hasRequirements(slotItems, player)) {
						//does not have the requirements...
						event.setCancelled(true);
						return;
					}

					ItemSlateEvent customEvent = new ItemSlateEvent(player, Click.ADD, si, slotItems);
					Bukkit.getServer().getPluginManager().callEvent(customEvent);

					si.addPermission(player);
				}
			} else {
				return;
			}
			//is not shift click
		} else {
			si = SlotItem.getFromStack(event.getCursor());
			if (event.getCursor() == null) {
				return;
			}
			//release the block
			if (event.getCursor().getType() != Material.AIR) {
				//int state = ImeJobs.getPlugin().playerData.getData(player.getName()).direction;
				if (si == null) return;
				//place the block up
				if (event.getRawSlot() < 54) {
					//all slotItems are in the list, now check.
					if (!si.hasRequirements(slotItems, player)) {
						//does not have the requirements...
						event.setCancelled(true);
						return;
					}
					//throw event
					ItemSlateEvent customEvent = new ItemSlateEvent(player, Click.ADD, si, slotItems);
					Bukkit.getServer().getPluginManager().callEvent(customEvent);

					si.addPermission(player);
					return;
				}
				//pickup slot
			}  else {
				si = SlotItem.getFromStack(event.getCurrentItem());
				if (si == null) return;
				if (event.getRawSlot() < 54) {

					//remove slot
					/*
					 * Health stone bug, and more to come:
					 * Health adds +2 for each health ability, when RELEASED in the top.
					 * Health removes 2 for each health ability, when RELEASED in the bottom.
					 * So if a player takes from top, puts it back, takes it, put it back. he can easily get unlimited health... and that sucks.
					 * 
					 */

					ItemSlateEvent customEvent = new ItemSlateEvent(player, Click.REMOVE, si, slotItems);
					Bukkit.getServer().getPluginManager().callEvent(customEvent);

					si.removePermission(player);
				} else {
				}
			}
		}

	}

	@EventHandler
	public void onInventoryClose (InventoryCloseEvent event) {
		if (!event.getInventory().getName().equalsIgnoreCase("Level-slate: " + event.getPlayer().getName())) return;

		Player player = (Player) event.getPlayer();
		int maxSlots = Integer.parseInt(ImeJobs.getPlugin().playerData.getData(player.getName()).getEntry("maxSlots"));

		for (int i = 53; i > maxSlots; i--) {
			event.getInventory().setItem(i, new ItemStack(Material.AIR));
		}

		String invString = InventoryString.InvToString(event.getInventory());

		ImeJobs.getPlugin().playerData.getData(player.getName()).setEntry("invString", invString);

		List<SlotItem> slots = InventoryString.getSlotItemsFromString(invString);

		for (SlotItem si : slots) {
			si.addPermission(player);
		}
	}

	private boolean canClick(int clickedSlot, int maxSlots) {
		System.out.println(clickedSlot);
		return (clickedSlot < maxSlots || clickedSlot > 53) && clickedSlot >= 0;
	}

}

package me.blockcat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryString {

	public static String InvToString(Inventory inventory) {
		String total = "";
		for (int i = 0; i < 54; i++) {
			ItemStack item = inventory.getContents()[i];

			if (item == null) continue;

			String s = "@" + i;
			ItemMeta meta = item.getItemMeta();
			try {
				String s1 = meta.getLore().get(0);
				if (s1.startsWith("#")) {
					s += s1;
				} else {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			total += s;
		}
		return total;
	}

	public static Inventory StringToInv (String string, String title) {
		Inventory inv = Bukkit.createInventory(null, 54, title);
		inv.setMaxStackSize(1);

		if (string == null || string.equalsIgnoreCase("")) return inv;

		String[] args = string.split("@");
		for (int i = 1; i < args.length; i++) {
			try {
				String s = args[i];
				String[] s1 = s.split("#");
				int slot = Integer.parseInt(s1[0].split(":")[0]);
				SlotItem slotItem = SlotItem.getFromId(s1[1]);

				ItemStack item = new ItemStack(slotItem.getIconId(), 1);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(slotItem.getName());
				
				List<String> lore = new ArrayList<String>();
				for (String l : slotItem.getLore()) {
					if (!l.startsWith(ChatColor.BLACK + "#")) lore.add(l); 
				}
				lore.add(ChatColor.BLACK + "#" + i);
				itemMeta.setLore(lore);
				
				item.setItemMeta(itemMeta);				
				inv.setItem(slot, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return inv;
	}

	public static List<SlotItem> getSlotItemsFromString(String string) {
		String[] args = string.split("@");
		List<SlotItem> list = new ArrayList<SlotItem>();

		for (int i = 1; i < args.length; i++) {
			String id = args[i].split("#")[1];
			list.add(SlotItem.getFromId(id));
		}

		return list;
	}
}

package me.blockcat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.blockcat.Requirements.Requirement;
import me.blockcat.Rewards.Power;
import me.blockcat.abilities.core.AbilityHandler.AbilityShop;
import me.blockcat.exceptions.IncorrectArgumentException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SlotItem {

	private static final File itemFile = new File(ImeJobs.getPlugin().getDataFolder(), "slotitems.yml");
	private static YamlConfiguration config = null;
	private String name;
	private String id;
	//Of the list, the first input is the ID: #AA0000
	private List<String> lore;
	private List<Requirement> requirements;
	private List<Power> stonePowers;
	private int iconId;

	private SlotItem (String name, int itemId, String id, List<String> lore, List<Requirement> requirements, List<Power> stonePowers) {
		this.name = name;
		this.iconId = itemId;
		this.id = id;
		this.lore = lore;
		this.requirements = requirements;
		this.stonePowers = stonePowers;
	}

	private static HashMap<String, SlotItem> slotItems = new HashMap<String, SlotItem>();

	public static SlotItem getFromId(String string) {
		if (slotItems.containsKey(string)) {
			return slotItems.get(string);
		}

		if (config == null) {
			config = new YamlConfiguration();
			try {
				config.load(itemFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ConfigurationSection configSection = config.getConfigurationSection(string);
		if (configSection == null) return null;

		String name = configSection.getString("name", "SlotStone"); 
		int itemId = configSection.getInt("itemIcon", 1);
		List<String> lore = configSection.getStringList("lore");
		lore.add(0, "#" + string);
		List<Requirement> reqs = new ArrayList<Requirement>();
		List<Power> powers = new ArrayList<Power>();

		for (String s : configSection.getStringList("requirements")) {
			Requirement req = null;
			try {
				req = Requirements.getRequirementFromString(s);
			} catch (IncorrectArgumentException e) {
				System.out.println("Wrong requirements in: " + string);
				continue;
			}
			reqs.add(req);
		}

		for (String s : configSection.getStringList("rewards")) {
			Power pow = null;
			try {
				pow = Rewards.getPowersFromString(s);
				powers.add(pow);
			} catch (IncorrectArgumentException e) {
				System.out.println("Wrong rewards in: " + string);
				continue;
			}
		}
		List<String> l1 = new ArrayList<String>();
		for (String s : lore) {
			s = colorString(s);
			l1.add(s);
		}
		lore = new ArrayList<String>(l1);

		slotItems.put(string, new SlotItem(name, itemId, string, lore, reqs, powers));
		return getFromId(string);
	}

	public static SlotItem getFromStack(ItemStack cursor) {
		if (cursor == null ||!cursor.hasItemMeta()) return null;
		ItemMeta im = cursor.getItemMeta();
		if (im == null) return null;
		if (!im.hasLore()) return null;
		String s = im.getLore().get(0);
		if (s == null || !s.startsWith("#")) return null;

		return getFromId(s.replace("#", ""));
	}

	private static String colorString(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public String getName() {
		return name;
	}

	public List<String> getLore() {
		return lore;
	}

	public String getId() {
		return id;
	}

	public int getIconId() {
		return iconId;
	}
	
	public List<AbilityShop> getAbilities() {
		//TODO: make this work
		List<AbilityShop> list = new ArrayList<AbilityShop>();
		for (Power p : stonePowers) {
			if (p.getFlag().equalsIgnoreCase("a")) {
				AbilityShop as = AbilityShop.valueOf(p.getValue());
				if (as != null) {
					list.add(as);
				}
			}
		}
		return list;
	}

	public boolean hasRequirements(List<SlotItem> slotItems2, Player player) {
		for (Requirement req : requirements) {
			String flag = req.getFlag();
			String value = req.getValue();
			boolean isOk = true;
			switch (flag) {
			case "p":
				isOk = ImeJobs.permission.has(player, value);
				break;
			case "-p":
				isOk = !ImeJobs.permission.has(player, value);
				break;
			case "s":
				isOk = containsId(slotItems2, value);
				break;
			case "-s":
				isOk = !containsId(slotItems2, value);
				break;
			}
			if (!isOk) {
				return false;
			}
		}
		return true;
	}

	public void addPermission(Player player) {
		for (Power p : stonePowers) {
			if (p.getFlag().equalsIgnoreCase("p")) {
				ImeJobs.permission.playerAdd(player, p.getValue());
			}
			if (p.getFlag().equalsIgnoreCase("g")) {
				ImeJobs.permission.playerAddGroup(player, p.getValue());
			}
		}
	}

	public void removePermission(Player player) {
		for (Power p : stonePowers) {
			if (p.getFlag().equalsIgnoreCase("p")) {
				ImeJobs.permission.playerRemove(player, p.getValue());
			}
			if (p.getFlag().equalsIgnoreCase("g")) {
				ImeJobs.permission.playerRemoveGroup(player, p.getValue());
			}
		}
	}

	private boolean containsId(List<SlotItem> list, String id) {
		for (SlotItem sl : list) {
			if (sl.getId().equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}
}

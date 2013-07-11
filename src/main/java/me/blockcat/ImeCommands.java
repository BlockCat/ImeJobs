package me.blockcat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ImeCommands implements CommandExecutor {

	private ImeJobs plugin;
	private Random random = new Random();

	public ImeCommands(ImeJobs plugin) {
		this.plugin = plugin;
		File playerDirs = new File(plugin.getDataFolder(), "/players/");

		if (!playerDirs.exists()) {
			playerDirs.mkdirs();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return true;
		}

		Player player = (Player)sender;

		if (!player.hasPermission("imejobs.use")) {
			player.sendMessage(ChatColor.RED + "[ImeJobs] You have no permissions to use this.");
			return true;
		}

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("type")) {
				if (ImeJobs.permission.has(player, "imejobs.admin.get")) { 
					if (args.length > 1) {
						SlotItem si = SlotItem.getFromId(args[1].toUpperCase());
						if (si == null) {
							player.sendMessage(ChatColor.RED + "Not a valid id");
							return true;
						}
						ItemStack i = new ItemStack(si.getIconId());

						ItemMeta m = i.getItemMeta();
						m.setDisplayName(si.getName());
						List<String> lore = new ArrayList<String>();
						for (String s : si.getLore()) {
							if (!s.startsWith(ChatColor.BLACK + "#")) lore.add(s); 
						}
						lore.add(ChatColor.BLACK + "#" + random.nextInt());
						m.setLore(lore);
						i.setItemMeta(m);

						player.getInventory().addItem(i);					
					}
				} else {
					player.sendMessage(ChatColor.RED + "You do not have permissions!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("help")) {
				showHelp(player);				
			}
			return true;
		}

		String invString = plugin.playerData.getData(player.getName()).getEntry("invString");

		Inventory inventory = InventoryString.StringToInv(invString, "Level-slate: " + player.getName());
		inventory.setMaxStackSize(1);
		player.openInventory(inventory);

		return true;
	}

	private void showHelp(Player player) {

	}

}

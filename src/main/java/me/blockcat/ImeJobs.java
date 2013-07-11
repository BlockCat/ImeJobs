package me.blockcat;

import java.io.File;
import java.io.IOException;

import me.blockcat.abilities.core.AbilityHandler;
import me.blockcat.abilities.core.ImeListener;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ImeJobs extends JavaPlugin{

	public static Permission permission = null;
	public static AbilityHandler abHandler = null;
	private static ImeJobs instance = null;

	public PlayerData playerData = null;
	private ImeConfig config = null;

	public void onEnable() {
		instance = this;
		playerData = new PlayerData();
		config = new ImeConfig(this);
		config.load();
		readyFiles();
		
		abHandler = new AbilityHandler();
		try {
			abHandler.addClasses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getCommand("ij").setExecutor(new ImeCommands(this));
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new ImeListener(), this);
		this.setupPermissions();
	}

	private void readyFiles() {
		File f = new File(this.getDataFolder(), "slotitems.yml"); 
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
			}
		}
	}

	public static ImeJobs getPlugin() {
		return instance;
	}

	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

}

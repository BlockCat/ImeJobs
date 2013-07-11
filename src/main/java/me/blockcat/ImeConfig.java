package me.blockcat;

import org.bukkit.configuration.file.FileConfiguration;

public class ImeConfig {
	
	private ImeJobs plugin;
	private FileConfiguration config = null;

	public ImeConfig(ImeJobs plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
	}
	
	public void load() {
		DEFAULT_SLOTS = getValue("defaultslots", 6);
		
		plugin.saveConfig();
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getValue(String key, T value) {
		if (config.contains(key)) {
			return (T) config.get(key);
		}
		config.set(key, value);
		return value;
		
	}
	
	public static int DEFAULT_SLOTS = 6;

}

package me.blockcat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {

	public HashMap<String, Data> dataMap = new HashMap<String, Data>();
	private static final File playerDirs = new File(ImeJobs.getPlugin().getDataFolder(), "/players/");

	public Data getData(String player){
		if (dataMap.containsKey(player)) return dataMap.get(player);

		Data data = new Data(player);
		data.load();
		data.save();

		dataMap.put(player, data);
		return data;
	}
	
	public class Data {
		private String player;
		private HashMap<String, String> entries = new HashMap<String, String>();
		/**
		 * 1 = picked up above.
		 * 0 = not clicked.
		 * -1 = picked up below.
		 */
		public int direction = 0;
		
		
		public Data(String player) {
			this.player = player;
		}
		//TODO make a load function too!
		public void save() {
			File file = new File(playerDirs, player + ".data");
			if (!file.exists()) {
				try {
					file.createNewFile();
					entries.put("maxSlots", ImeConfig.DEFAULT_SLOTS + "");
					entries.put("invString", "");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			YamlConfiguration data = new YamlConfiguration();
			try {
				data.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (Entry<String, String> m : entries.entrySet()) {
				data.set(m.getKey(), m.getValue());
			}
			try {
				data.save(file);
			} catch (IOException e) {
			}
		}
		
		public void load() {
			File file = new File(playerDirs, player + ".data");
			if (!file.exists()) {
				try {
					file.createNewFile();
					entries.put("maxSlots", ImeConfig.DEFAULT_SLOTS + "");
					entries.put("invString", "");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			YamlConfiguration data = new YamlConfiguration();
			try {
				data.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (String s : data.getKeys(true)) {
				entries.put(s, data.getString(s));
			}
		}
		
		public String getEntry(String key) {
			if (entries.containsKey(key)) return entries.get(key);
			
			return null;
		}
		public void setEntry(String key, String value) {
			entries.put(key, value);
			save();
		}
		public int getMaxSlots() {
			return Integer.parseInt(getEntry("maxSlots"));
		}
		
		public String getInventoryString() {
			return getEntry("invString");
		}
	}

}

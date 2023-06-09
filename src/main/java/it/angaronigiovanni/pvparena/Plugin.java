package it.angaronigiovanni.pvparena;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import it.angaronigiovanni.pvparena.commands.DisablePluginCommand;
import it.angaronigiovanni.pvparena.commands.MinigameCommand;
import it.angaronigiovanni.pvparena.dimensions.MinigameType;


public class Plugin extends JavaPlugin{
	public static Plugin plugin;
	public static java.util.logging.Logger logger;
	
	@Override
	public void onEnable() {
		plugin = this;
		logger = Plugin.plugin.getLogger();

		FileConfiguration config = getConfig();
		if(config.saveToString().isEmpty()) {
			saveDefaultConfig();
			config = getConfig();
			config.set("minigames", MinigameType.types);
			saveConfig();
		}
		

		this.getCommand("minigame").setExecutor(new MinigameCommand());
		this.getCommand("disable").setExecutor(new DisablePluginCommand());
	}
		
	@Override
	public void onDisable() {
		Bukkit.getServer().getPluginManager().disablePlugin(this);
	}
	
	public void disablePlugin() {
		Bukkit.getServer().getPluginManager().disablePlugin(this);
	}
}

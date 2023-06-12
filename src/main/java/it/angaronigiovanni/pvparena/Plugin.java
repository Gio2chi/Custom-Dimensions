package it.angaronigiovanni.pvparena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import it.angaronigiovanni.pvparena.commands.DisablePluginCommand;
import it.angaronigiovanni.pvparena.commands.MinigameCommand;
import it.angaronigiovanni.pvparena.commands.SetSpawnCommand;
import it.angaronigiovanni.pvparena.commands.SpawnCommand;
import it.angaronigiovanni.pvparena.dimensions.MinigameType;
import it.angaronigiovanni.pvparena.events.joinOnUnloadedDimensionEventHandler;


public class Plugin extends JavaPlugin{
	public static Plugin plugin;
	public static java.util.logging.Logger logger;
	
	public static ChatColor errorColor = ChatColor.DARK_RED;
	public static ChatColor successColor = ChatColor.GREEN;
	
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

		// events
		getServer().getPluginManager().registerEvents(new joinOnUnloadedDimensionEventHandler(), this);
		
		// commands
		this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
		this.getCommand("spawn").setExecutor(new SpawnCommand());
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

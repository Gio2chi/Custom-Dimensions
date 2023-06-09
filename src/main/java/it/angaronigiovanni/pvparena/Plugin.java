package it.angaronigiovanni.pvparena;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import it.angaronigiovanni.pvparena.commands.DisablePluginCommand;
import it.angaronigiovanni.pvparena.commands.MinigameCommand;
import it.angaronigiovanni.pvparena.dimensions.Minigame;
import it.angaronigiovanni.pvparena.dimensions.MinigameType;


public class Plugin extends JavaPlugin{
	public static Plugin plugin;
	public static java.util.logging.Logger logger;
	
	@Override
	public void onEnable() {
		plugin = this;
		logger = Plugin.plugin.getLogger();

		// try {
		// 	Minigame pvpArena = new Minigame("PVPArena", MinigameType.PVP);
		// 	pvpArena.load();
		// 	pvpArena.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		// 	pvpArena.getWorld().setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		// } catch (IOException e) { e.printStackTrace(); }
		

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

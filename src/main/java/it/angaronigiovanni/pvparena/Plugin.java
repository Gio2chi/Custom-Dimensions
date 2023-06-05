package it.angaronigiovanni.pvparena;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import it.angaronigiovanni.pvparena.commands.DisablePluginCommand;
import it.angaronigiovanni.pvparena.commands.TeleportToArena;


public class Plugin extends JavaPlugin{
	
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		WorldCreator worldCreator = new WorldCreator("PVPArena");
		World PVPArena = worldCreator.createWorld();
		PVPArena.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		PVPArena.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		
		this.getCommand("PVPArena").setExecutor(new TeleportToArena());
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

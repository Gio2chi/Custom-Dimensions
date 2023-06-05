package it.angaronigiovanni.pvparena.commands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DisablePluginCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender instanceof Player && sender.isOp()) || sender instanceof ConsoleCommandSender) {
			if(args.length == 0) {
				it.angaronigiovanni.pvparena.Plugin.plugin.disablePlugin();
			}else if(args.length == 1 && Bukkit.getServer().getPluginManager().getPlugin(args[0]) != null) {
				Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin(args[0]));
				
			}else {
				sender.sendMessage("errore di sintassi / Plugin inesistente");
			}
		}
		
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			List<String> pluginsNames = new ArrayList<String>();
			Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();
			
			for(int i = 0; i != plugins.length; i++) {
				pluginsNames.add(plugins[i].getName());
			}
			return pluginsNames;
		}
		
		return null;
	}
	
	/*public void unregisterCommands() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
		field.setAccessible(true);
		CommandMap map = (CommandMap) field.get(Bukkit.getServer());
		map.clearCommands();
		map.getCommand(null).unregister(map);
	}*/
}

package it.angaronigiovanni.pvparena.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class TeleportToArena implements TabExecutor{
	
	Server server = Bukkit.getServer();
	World overworld = server.getWorlds().get(0);

	/*
	 	/PVPArena 
	 	 * teleport the sender to a random arena
	 	
	 	/PVPArena dimension
	 	 * teleport the sender to a specific arena (other dimensions too but without the Tab Completition)
	 	 
	 	/PVPArena player 
	 	 * teleport "player" to a random arena (also multiple players)
	 	
	 	/PVPArena player dimension
	 	 * teleport "player" to a specific arena (other dimensions too but without the Tab Completition) (also multiple players)
	 	  
	 	if the sender/player is in an Arena the dimension will be the overworld
	*/
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && sender.isOp()) {
			Player player = (Player) sender;
			Boolean solo = true;
			World dim = randomDimension();
			
			if(args.length == 1) {
				
				if(server.getWorld(args[0]) != null) {
					dim = server.getWorld(args[0]);
				}else if(server.getPlayer(args[0]) != null){
					player = server.getPlayer(args[0]);
				}else if(args[0].equals("@a")) {
					solo = false;
				}else {
					player.sendMessage("The player is not Online or the dimension doesn't exist");
					return false;
				}
				
			}else if(args.length == 2) {
				
				if(server.getPlayer(args[0]) != null) {
					player = server.getPlayer(args[0]);
				}else if(args[0].equals("@a")) {
					solo = false;
				}else {
					player.sendMessage("The player is not Online");
					return false;
				}
				
				if(server.getWorld(args[1]) != null) {
					dim = server.getWorld(args[1]);
				}else {
					player.sendMessage("The World doesn't exist");
					return false;
				}
				
			}else if(args.length != 0) {
				player.sendMessage("sbagliata sintassi del comando /PVPArena");
				return false;
			}
			
			if(solo) {
				if(player.getWorld() == dim) {
					player.setGameMode(GameMode.SURVIVAL);
					player.teleport(new Location(overworld, -208, 70, 80));
				} else {
					player.setGameMode(GameMode.ADVENTURE);
					player.teleport(new Location(dim, 268, 27, 10));
				}
			} else {
				Player[] OnlinePlayers = new Player[server.getOnlinePlayers().size()];
				server.getOnlinePlayers().toArray(OnlinePlayers);

				for(int i = 0; i != OnlinePlayers.length; i++) {
					OnlinePlayers[i].teleport(new Location(dim, 268, 27, 10));
				}
			}
			
			
		}
		return true;
	}
	
	private World randomDimension() {
		int rand = (int) (Math.random() * (server.getWorlds().size() - 3)) + 3;
		return server.getWorlds().get(rand);
	}
	
	/*
		/PVPArena tabCompleter[0] tabCompleter[1] 
 	 	 * tabCompleter[0] will display all dimensions and all players (with @a)
 	 	 * tabCompleter[1] will display all dimensions only if the first argument is an Online player
 	*/

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
	
		//tabCompleter[0]
		if(args.length == 1) {
			List<String> result = new ArrayList<>();
		
			World[] dimensions = new World[server.getWorlds().size()];
			server.getWorlds().toArray(dimensions);
		
			for (int i = 0; i != dimensions.length; i++) {
				result.add(dimensions[i].getName());
			}
		
			Player[] players = new Player[server.getOnlinePlayers().size()];
			server.getOnlinePlayers().toArray(players);
		
			for(int i = 0; i != players.length; i++) {
				result.add(players[i].getName());
			}
		
			result.add("@a");
		
			return result;
		}
		
		if(args.length == 2 && server.getPlayer(args[1]) != null) {
			List<String> result = new ArrayList<>();
			
			World[] dimensions = new World[server.getWorlds().size()];
			server.getWorlds().toArray(dimensions);
		
			for (int i = 0; i != dimensions.length; i++) {
				result.add(dimensions[i].getName());
			}
			
			return result;
		}
	
		return null;
	}
}

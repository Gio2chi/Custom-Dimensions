package it.angaronigiovanni.pvparena.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import it.angaronigiovanni.pvparena.dimensions.Minigame;
import it.angaronigiovanni.pvparena.dimensions.MinigameType;

public class MinigameCommand implements TabExecutor{
	
	Server server = Bukkit.getServer();
	World overworld = server.getWorlds().get(0);
	Logger logger = Bukkit.getLogger();

	/*
	 	/minigame 
	 	 * teleport the sender to a random arena
	 	
	 	/minigame dimension
	 	 * teleport the sender to a specific arena (other dimensions too but without the Tab Completition)
	 	 
	 	/minigame player 
	 	 * teleport "player" to a random arena (also multiple players)
	 	
	 	/minigame player dimension
	 	 * teleport "player" to a specific arena (other dimensions too but without the Tab Completition) (also multiple players)
	 	  
	 	if the sender/player is in an Arena the dimension will be the overworld
	*/
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// loading new minigame maps without restarting the server
		if( args[0].equalsIgnoreCase("load") ) {

			if( !sender.isOp() ){ 
				sender.sendMessage("&4An administrator permission is required"); 
				return false;
			}
			if( args.length > 3 ) {
				sender.sendMessage("Syntax error: try with /minigame load [dimension_name] or /minigame load [dimension_name] [minigame_type] \n default minigame type is survival");
				return false;
			}

			if( !Files.exists(new File(Minigame.folder.toPath() + "/" + args[1]).toPath()) ) {
				sender.sendMessage(args[1] + " doesnt exist");
				return false;
			}

			try {
				int type = MinigameType.SURVIVAL;
				if( args[2] != null ) type = MinigameType.parseType(args[2]);
				Minigame minigame = new Minigame(args[1], type);
				minigame.load();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				sender.sendMessage("IOException encountered");
				return false;
			}
		}

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
 	 	 * tabComplete[0] will display all dimensions and all players (with @a)
 	 	 * tabComplete[1] will display all dimensions only if the first argument is an Online player
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

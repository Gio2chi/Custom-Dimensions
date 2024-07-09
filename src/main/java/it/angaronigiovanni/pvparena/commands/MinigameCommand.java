package it.angaronigiovanni.pvparena.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import it.angaronigiovanni.pvparena.Plugin;
import it.angaronigiovanni.pvparena.dimensions.Minigame;
import it.angaronigiovanni.pvparena.dimensions.MinigameType;

public class MinigameCommand implements TabExecutor {

	Server server = Bukkit.getServer();
	World overworld = server.getWorlds().get(0);
	Logger logger = Bukkit.getLogger();
	ChatColor errorColor = Plugin.errorColor;
	ChatColor successColor = Plugin.successColor;

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		// tab completer for the /minigame command
		if (args.length == 1) {
			List<String> result = new ArrayList<>();

			result.add("tp");
			result.add("exit");

			if (!sender.isOp())
				return result;

			result.add("stop");

			result.add("load");
			result.add("unload");

			return result;
		}

		// tab completer for the minigame tp command
		if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
			List<String> result = new ArrayList<>();

			// get all the minigames
			try {
				for (String name : Minigame.getAll().keySet()) {
					if (Minigame.get(name).getWorld() != null)
						result.add(name);
				}
			} catch (IOException e) { e.printStackTrace(); }

			if (!sender.isOp())
				return result;

			// get all the teams
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getMainScoreboard();

			Set<Team> teams = board.getTeams();
			for (Team team : teams)
				result.add(team.getName());

			// get all the players
			Player[] onlinePlayers = new Player[server.getOnlinePlayers().size()];
			server.getOnlinePlayers().toArray(onlinePlayers);

			for (Player player : onlinePlayers)
				result.add(player.getName());

			return result;
		}

		// tab completer for the minigame exit command
		if (args.length == 2 && args[0].equalsIgnoreCase("exit")) {
			if (!sender.isOp())
				return null;
			
			List<String> result = new ArrayList<>();
			return result;
		}

		// tab completer for the minigame stop command
		if (args.length == 2 && args[0].equalsIgnoreCase("stop")) {
			if (!sender.isOp())
				return null;
			
			List<String> result = new ArrayList<>();

			try {
				for (String name : Minigame.getAll().keySet()) {
					if (Minigame.get(name).getWorld() != null)
						result.add(name);
				}
			} catch (IOException e) { e.printStackTrace(); }
			
			return result;
		}

		// tab completer for the minigame load command
		if (args.length == 2 && args[0].equalsIgnoreCase("load")) {
			if (!sender.isOp())
				return null;

			File folder = new File("minigames/");

			List<String> dirs = new ArrayList<>();

			for (File dir : folder.listFiles())
				dirs.add(dir.toString().split(Pattern.quote(File.separator))[1]);

			try {
				for (String name : Minigame.getAll().keySet()) {
					if (Minigame.get(name).getWorld() != null)
						dirs.remove(name);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return dirs;
		}

		// tab completer for the minigame unload command
		if (args.length == 2 && args[0].equalsIgnoreCase("unload")) {
			if (!sender.isOp())
				return null;

			List<String> result = new ArrayList<>();

			try {
				for (String name : Minigame.getAll().keySet()) {
					if (Minigame.get(name).getWorld() != null)
						result.add(name);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}

		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// load new minigame maps without restarting the server
		if (args.length != 0 && args[0].equalsIgnoreCase("load")) {

			if (!sender.isOp()) {
				sender.sendMessage(errorColor + "An administrator permission is required");
				return true;
			}
			if (args.length > 3 || args.length < 2) {
				sender.sendMessage(errorColor
						+ "Syntax error: try with /minigame load [dimension_name] or /minigame load [dimension_name] [minigame_type] \nDefault minigame type is SURVIVAL");
				return true;
			}

			if (!Files.exists(new File(Minigame.folder.toPath() + "/" + args[1]).toPath())) {
				sender.sendMessage(errorColor + args[1]
						+ " doesnt exist \nSyntax error: try with /minigame load [dimension_name] or /minigame load [dimension_name] [minigame_type] \n"
						+ "Default minigame type is SURVIVAL");
				return true;
			}

			try {
				int configType = MinigameType
						.parseType((String) Plugin.plugin.getConfig().get("minigames." + args[1] + ".type"));

				int type = configType != -1 ? configType : MinigameType.SURVIVAL;
				if (args.length == 3)
					type = MinigameType.parseType(args[2]);
				if (type == -1) {
					sender.sendMessage(errorColor + args[2]
							+ " isnt an available mode \nSyntax error: try with /minigame load [dimension_name] or /minigame load [dimension_name] [minigame_type] \n"
							+ "Default minigame type is SURVIVAL");
					return true;
				}
				Minigame minigame = new Minigame(args[1], type);
				minigame.load();

				sender.sendMessage(successColor + args[1] + " is loaded successfully");

				return true;
			} catch (IOException e) { e.printStackTrace(); return true; }
		}

		// unload minigame map without restarting the server
		if (args.length != 0 && args[0].equalsIgnoreCase("unload")) {

			if (!sender.isOp()) {
				sender.sendMessage(errorColor + "An administrator permission is required");
				return true;
			}
			if (args.length != 2) {
				sender.sendMessage(errorColor + "Syntax error: try with /minigame unload [dimension_name]");
				return true;
			}

			if (Bukkit.getWorld("minigames/" + args[1]) == null) {
				sender.sendMessage(errorColor + args[1] + " doesnt exist"
						+ "\nSyntax error: try with /minigame unload [dimension_name]");
				return true;
			}

			try {
				Minigame minigame = Minigame.get(args[1]);

				if (minigame == null) {
					sender.sendMessage(errorColor + args[1] + " isnt a minigame"
							+ "\nSyntax error: try with /minigame unload [dimension_name]");
					return true;
				}

				minigame.unload();
				sender.sendMessage(successColor + args[1] + " is unloaded successfully");
				return true;

			} catch (IOException e) { e.printStackTrace(); return true; }
		}

		// tp to minigame map
		if (args.length != 0 && args[0].equalsIgnoreCase("tp")) {

			if (!(sender instanceof Player)) {
				if (args.length == 1) {
					sender.sendMessage(errorColor + "Must be a player to run this command this way"
							+ "\nTry \\minigame tp [ team_name | player_name ] ?[ minigame ]");
					return true;
				} else if (args.length == 2) {
					Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
					Set<Team> teams = board.getTeams();
					Boolean success = false;
					try {
						Minigame minigame = randomDimension();

						if (minigame == null) {
							sender.sendMessage(errorColor + "No minigame loaded");
							return true;
						}

						if (Bukkit.getPlayer(args[1]) != null) {
							Bukkit.getPlayer(args[1]).teleport(minigame.getSpawnLocation());
							return true;
						}

						for (Team team : teams)
							if (args[1].equalsIgnoreCase(team.getName())) {
								success = true;
								for (String entry : team.getEntries())
									if (Bukkit.getPlayer(entry) != null)
										Bukkit.getPlayer(entry).teleport(minigame.getSpawnLocation());
							}

						if (!success)
							sender.sendMessage(errorColor
									+ "Syntax Error: Try \\minigame tp [ team_name | player_name ] ?[ minigame ]");

						return true;
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				} else if (args.length == 3) {
					try {
						Minigame minigame = Minigame.get(args[2]);

						if (minigame == null) {
							sender.sendMessage(errorColor + args[3] + "is not a minigame");
							return true;
						}

						Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
						Set<Team> teams = board.getTeams();
						Boolean success = false;

						if (Bukkit.getPlayer(args[1]) != null) {
							Bukkit.getPlayer(args[1]).teleport(minigame.getSpawnLocation());
							return true;
						}

						for (Team team : teams)
							if (args[1].equalsIgnoreCase(team.getName())) {
								success = true;
								for (String entry : team.getEntries())
									if (Bukkit.getPlayer(entry) != null)
										Bukkit.getPlayer(entry).teleport(minigame.getSpawnLocation());
							}

						if (!success)
							sender.sendMessage(errorColor
									+ "Syntax Error: Try \\minigame tp [ team_name | player_name ] ?[ minigame ]");

						return true;
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				} else {
					sender.sendMessage(errorColor
							+ "Syntax Error: Try \\minigame tp [ team_name | player_name ] ?[ minigame ]");
					return true;
				}
			}

			Player player = (Player) sender;

			// /minigame tp ?[map]
			if (!player.isOp()) {
				if (args.length == 1) {
					try {
						Minigame minigame = randomDimension();

						if (minigame == null)
							player.sendMessage(errorColor + "No minigame loaded. Ask admins to load a minigame");
						else
							player.teleport(minigame.getSpawnLocation());

						return true;

					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				} else if (args.length == 2) {
					try {
						Minigame minigame = Minigame.get(args[1]);

						if (minigame == null) {
							player.sendMessage(errorColor + args[1] + " isnt a minigame"
									+ "\nSyntax error: try with /minigame tp [dimension_name] or /minigame tp");
							return true;
						} else {
							player.teleport(minigame.getSpawnLocation());
							return true;
						}
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}

			if (args.length == 1) {
				try {
					Minigame minigame = randomDimension();

					player.teleport(minigame.getSpawnLocation());
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else if (args.length == 2) {
				try {
					Minigame minigame = Minigame.get(args[1]);

					if (minigame != null) {
						player.teleport(minigame.getSpawnLocation());
						return true;
					}

					Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
					Set<Team> teams = board.getTeams();
					Boolean success = false;
					minigame = randomDimension();

					if (minigame == null) {
						sender.sendMessage(errorColor + "No minigame loaded");
						return true;
					}

					if (Bukkit.getPlayer(args[1]) != null) {
						Bukkit.getPlayer(args[1]).teleport(minigame.getSpawnLocation());
						return true;
					}

					for (Team team : teams)
						if (args[1].equalsIgnoreCase(team.getName())) {
							success = true;
							for (String entry : team.getEntries())
								if (Bukkit.getPlayer(entry) != null)
									Bukkit.getPlayer(entry).teleport(minigame.getSpawnLocation());
						}

					if (!success)
						sender.sendMessage(errorColor
								+ "Syntax Error: Try \\minigame tp [ team_name | player_name | minigame ] ?[ minigame ]");

					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else if (args.length == 3) {
				try {
					Minigame minigame = Minigame.get(args[2]);

					if (minigame == null) {
						sender.sendMessage(errorColor + args[3] + "is not a minigame");
						return true;
					}

					Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
					Set<Team> teams = board.getTeams();
					Boolean success = false;

					if (Bukkit.getPlayer(args[1]) != null) {
						Bukkit.getPlayer(args[1]).teleport(minigame.getSpawnLocation());
						return true;
					}

					for (Team team : teams)
						if (args[1].equalsIgnoreCase(team.getName())) {
							success = true;
							for (String entry : team.getEntries())
								if (Bukkit.getPlayer(entry) != null)
									Bukkit.getPlayer(entry).teleport(minigame.getSpawnLocation());
						}

					if (!success)
						sender.sendMessage(errorColor
								+ "Syntax Error: Try \\minigame tp [ team_name | player_name ] ?[ minigame ]");

					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				sender.sendMessage(errorColor
						+ "Syntax Error: Try \\minigame tp [ team_name | player_name ] ?[ minigame ]");
				return true;
			}

			return false;
		}

		return false;
	}

	private Minigame randomDimension() throws IOException {
		Map<String, Minigame> minigames = Minigame.getAll();

		if (minigames.size() == 0)
			return null;

		int rand = (int) (Math.random() * minigames.size());
		String[] keys = (String[]) minigames.keySet().toArray();

		return minigames.get(keys[rand]);
	}
}
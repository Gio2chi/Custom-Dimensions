package it.angaronigiovanni.pvparena.commands;

import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import it.angaronigiovanni.pvparena.Plugin;
import it.angaronigiovanni.pvparena.dimensions.Minigame;

public class SetSpawnCommand implements TabExecutor {

    ChatColor errorColor = Plugin.errorColor;
    ChatColor successColor = Plugin.successColor;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(errorColor + "Syntax error: try with /respawn");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(errorColor + "Must be a player to run this command");
            return true;
        }

        Player player = (Player) sender;
        if (!player.isOp()) {
            sender.sendMessage(errorColor + "An administrator permission is required");
            return true;
        }

        try {
            String[] dimName = player.getWorld().getName().split("/");
            Minigame dim = null;
            if(dimName.length == 2)
                dim = Minigame.get(dimName[1]);
                    
            if (dim == null) {
                player.getWorld().setSpawnLocation(player.getLocation());
            } else {
                dim.setSpawnLocation(player.getLocation());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}

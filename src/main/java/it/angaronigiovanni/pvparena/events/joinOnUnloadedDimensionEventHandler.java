package it.angaronigiovanni.pvparena.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import it.angaronigiovanni.pvparena.Plugin;

public class joinOnUnloadedDimensionEventHandler implements Listener {

    private FileConfiguration configFile;
    private ConfigurationSection playersData;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        configFile = Plugin.plugin.getConfig();
        playersData = configFile.getConfigurationSection("players");

        if(this.configFile.get("players") == null) return;
        ConfigurationSection playerData = this.playersData.getConfigurationSection(uuid.toString());
        if( playerData != null && (String) playerData.get("location.world") != event.getPlayer().getWorld().getName() ) {
            player.teleport(player.getBedSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        
        configFile = Plugin.plugin.getConfig();

        configFile.set("players." + uuid.toString() + ".location.world", event.getPlayer().getWorld().getName());
        Plugin.plugin.saveConfig();
    }
}

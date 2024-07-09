package it.angaronigiovanni.pvparena.dimensions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import it.angaronigiovanni.pvparena.Plugin;

public class Minigame extends Dimension {
    private static Map<String, Minigame> instances = new HashMap<>();

    public static final File folder = new File("minigames/");
    public int type;

    private Location location;

    public Minigame(String name, int type) throws IOException {
        super(name, (new File("minigames/").toPath()));
        this.type = type;

        instances.put(name, this);
    }

    public static Minigame get(String name) throws IOException {
        return instances.get(name);
    }
    public static Map<String, Minigame> getAll() throws IOException {
        return instances;
    }

    @Override
    public void load() {
        if (this.dim == null) {
            this.dim = this.worldCreator.createWorld(); // Load the world

            FileConfiguration configFile = Plugin.plugin.getConfig();
            configFile.set("minigames." + this.name + ".type", MinigameType.types[this.type]);

            ConfigurationSection gamerules = configFile.getConfigurationSection("minigames." + this.name + ".gamerules");

            if (gamerules != null) {
                for (String key : gamerules.getKeys(false)) {
                    this.setGameRule(key, gamerules.get(key));
                }
            }

            Boolean hardcore = (Boolean) configFile.get("minigames." + this.name + ".hardocore");
            Boolean pvp = (Boolean) configFile.get("minigames." + this.name + ".pvp");
            Boolean autosave = (Boolean) configFile.get("minigames." + this.name + ".autosave");

            if (hardcore != null) {
                this.dim.setHardcore(hardcore);
            }
            if (pvp != null) {
                this.dim.setPVP(pvp);
            }
            if (autosave != null) {
                this.dim.setAutoSave(autosave);
            }

            Plugin.plugin.saveConfig();
        }
    }

    private void setGameRule(String name, Object value) {
        switch (name) {
            case "announceAdvancements":
                this.dim.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, (Boolean) value);
                break;
            case "commandBlockOutput":
                this.dim.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, (Boolean) value);
                break;
            case "disableElytraMovementCheck":
                this.dim.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, (Boolean) value);
                break;
            case "doDaylightCycle":
                this.dim.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, (Boolean) value);
                break;
            case "doEntityDrops":
                this.dim.setGameRule(GameRule.DO_ENTITY_DROPS, (Boolean) value);
                break;
            case "doFireTick":
                this.dim.setGameRule(GameRule.DO_FIRE_TICK, (Boolean) value);
                break;
            case "doLimitedCrafting":
                this.dim.setGameRule(GameRule.DO_LIMITED_CRAFTING, (Boolean) value);
                break;
            case "doMobLoot":
                this.dim.setGameRule(GameRule.DO_MOB_LOOT, (Boolean) value);
                break;
            case "doMobSpawning":
                this.dim.setGameRule(GameRule.DO_MOB_SPAWNING, (Boolean) value);
                break;
            case "doTileDrops":
                this.dim.setGameRule(GameRule.DO_TILE_DROPS, (Boolean) value);
                break;
            case "doWeatherCycle":
                this.dim.setGameRule(GameRule.DO_WEATHER_CYCLE, (Boolean) value);
                break;
            case "keepInventory":
                this.dim.setGameRule(GameRule.KEEP_INVENTORY, (Boolean) value);
                break;
            case "logAdminCommands":
                this.dim.setGameRule(GameRule.LOG_ADMIN_COMMANDS, (Boolean) value);
                break;
            case "mobGriefing":
                this.dim.setGameRule(GameRule.MOB_GRIEFING, (Boolean) value);
                break;
            case "naturalRegeneration":
                this.dim.setGameRule(GameRule.NATURAL_REGENERATION, (Boolean) value);
                break;
            case "reducedDebugInfo":
                this.dim.setGameRule(GameRule.REDUCED_DEBUG_INFO, (Boolean) value);
                break;
            case "sendCommandFeedback":
                this.dim.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, (Boolean) value);
                break;
            case "showDeathMessages":
                this.dim.setGameRule(GameRule.SHOW_DEATH_MESSAGES, (Boolean) value);
                break;
            case "spectatorsGenerateChunks":
                this.dim.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, (Boolean) value);
                break;
            case "disableRaids":
                this.dim.setGameRule(GameRule.DISABLE_RAIDS, (Boolean) value);
                break;
            case "doInsomnia":
                this.dim.setGameRule(GameRule.DO_INSOMNIA, (Boolean) value);
                break;
            case "doImmediateRespawn":
                this.dim.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, (Boolean) value);
                break;
            case "drowningDamage":
                this.dim.setGameRule(GameRule.DROWNING_DAMAGE, (Boolean) value);
                break;
            case "fallDamage":
                this.dim.setGameRule(GameRule.FALL_DAMAGE, (Boolean) value);
                break;
            case "fireDamage":
                this.dim.setGameRule(GameRule.FIRE_DAMAGE, (Boolean) value);
                break;
            case "freezeDamage":
                this.dim.setGameRule(GameRule.FREEZE_DAMAGE, (Boolean) value);
                break;
            case "doPatrolSpawning":
                this.dim.setGameRule(GameRule.DO_PATROL_SPAWNING, (Boolean) value);
                break;
            case "doTraderSpawning":
                this.dim.setGameRule(GameRule.DO_TRADER_SPAWNING, (Boolean) value);
                break;
            case "doWardenSpawning":
                this.dim.setGameRule(GameRule.DO_WARDEN_SPAWNING, (Boolean) value);
                break;
            case "forgiveDeadPlayers":
                this.dim.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, (Boolean) value);
                break;
            case "universalAnger":
                this.dim.setGameRule(GameRule.UNIVERSAL_ANGER, (Boolean) value);
                break;
            case "blockExplosionDropDecay":
                this.dim.setGameRule(GameRule.BLOCK_EXPLOSION_DROP_DECAY, (Boolean) value);
                break;
            case "mobExplosionDropDecay":
                this.dim.setGameRule(GameRule.MOB_EXPLOSION_DROP_DECAY, (Boolean) value);
                break;
            case "tntExplosionDropDecay":
                this.dim.setGameRule(GameRule.TNT_EXPLOSION_DROP_DECAY, (Boolean) value);
                break;
            case "waterSourceConversion":
                this.dim.setGameRule(GameRule.WATER_SOURCE_CONVERSION, (Boolean) value);
                break;
            case "lavaSourceConversion":
                this.dim.setGameRule(GameRule.LAVA_SOURCE_CONVERSION, (Boolean) value);
                break;
            case "globalSoundEvents":
                this.dim.setGameRule(GameRule.GLOBAL_SOUND_EVENTS, (Boolean) value);
                break;
            case "doVinesSpread":
                this.dim.setGameRule(GameRule.DO_VINES_SPREAD, (Boolean) value);
                break;
            case "randomTickSpeed":
                this.dim.setGameRule(GameRule.RANDOM_TICK_SPEED, (Integer) value);
                break;
            case "spawnRadius":
                this.dim.setGameRule(GameRule.SPAWN_RADIUS, (Integer) value);
                break;
            case "maxEntityCramming":
                this.dim.setGameRule(GameRule.MAX_ENTITY_CRAMMING, (Integer) value);
                break;
            case "maxCommandChainLength":
                this.dim.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, (Integer) value);
                break;
            case "commandModificationBlockLimit":
                this.dim.setGameRule(GameRule.COMMAND_MODIFICATION_BLOCK_LIMIT, (Integer) value);
                break;
            case "playersSleepingPercentage":
                this.dim.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, (Integer) value);
                break;
            case "snowAccumulationHeight":
                this.dim.setGameRule(GameRule.SNOW_ACCUMULATION_HEIGHT, (Integer) value);
                break;
        }
    }

    
    public Location getSpawnLocation() {

        if(this.location != null) return this.location;
        
        FileConfiguration config = Plugin.plugin.getConfig();

        ConfigurationSection section = config.getConfigurationSection("minigames." + this.name);

        if (section == null) {
            section = config.getConfigurationSection("minigames");
            section.set( this.name + ".spawn.X", 0);
            section.set( this.name + ".spawn.Y", 60);
            section.set( this.name + ".spawn.Z", 0);
            section.set( this.name + ".spawn.yaw", 0);
            section.set( this.name + ".spawn.pitch", 0);

            Plugin.plugin.saveConfig();

            return new Location(this.dim, 0, 60, 0, 0, 0);
        }

        ConfigurationSection spawn = section.getConfigurationSection("spawn");

        if( spawn == null ) {
            section.set( "spawn.X", 0);
            section.set( "spawn.Y", 60);
            section.set( "spawn.Z", 0);
            section.set( "spawn.yaw", 0);
            section.set( "spawn.pitch", 0);

            Plugin.plugin.saveConfig();

            return new Location(this.dim, 0, 60, 0, 0, 0);
        }

        this.location = new Location(this.dim, (int) spawn.get("X"), (int) spawn.get("Y"), (int) spawn.get("Z"), (int) spawn.get("yaw"), (int) spawn.get("pitch"));

        return location;
    }

    public void setSpawnLocation(Location location) {
        
        Plugin.plugin.getConfig().set("minigames." + this.name + ".spawn.X", (int) location.getX());
        Plugin.plugin.getConfig().set("minigames." + this.name + ".spawn.Y", (int) location.getY());
        Plugin.plugin.getConfig().set("minigames." + this.name + ".spawn.Z", (int) location.getZ());
        Plugin.plugin.getConfig().set("minigames." + this.name + ".spawn.yaw", (int) location.getYaw());
        Plugin.plugin.getConfig().set("minigames." + this.name + ".spawn.pitch", (int) location.getPitch());

        Plugin.plugin.saveConfig();

        this.location = location;
    }
}
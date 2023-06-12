package it.angaronigiovanni.pvparena.dimensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class Dimension {
    protected String name;
    protected World dim;
    protected WorldCreator worldCreator;

    public Dimension(String dimName) throws IOException {

        this.name = dimName;

        if (!Files.exists(new File(this.name).toPath()))
            throw new Error(this.name + " doesnt exist", null);

        this.worldCreator = this.create();
    }

    public Dimension(String dimName, Path dimsPath) throws IOException {
        this.name = dimName;

        if (!Files.exists(dimsPath))
            Files.createDirectories(dimsPath);

        File world = new File(dimsPath.toString() + "/" + this.name);

        if (!Files.exists(world.toPath()))
            throw new Error(this.name + " doesnt exist", null);

        this.worldCreator = this.create(dimsPath);
    }

    private WorldCreator create() {
        return new WorldCreator(this.name);
    }

    private WorldCreator create(Path folder) {
        return new WorldCreator(folder.toString() + "/" + this.name);
    }

    public void load() {
        if (this.dim == null) {
            this.dim = this.worldCreator.createWorld(); // Load the world
        }
    }
    
    public void unload() throws IOException, FileNotFoundException {
        List<Player> players = this.dim.getPlayers();
        for (Player player : players) {
            Location playerSpawnLocation = player.getBedSpawnLocation();
            World overworld = Bukkit.getWorlds().get(0);

            if (playerSpawnLocation.getWorld().equals(overworld)) {
                player.teleport(playerSpawnLocation);
            } else {
                player.teleport(overworld.getSpawnLocation());
            }
        }
        this.dim.save();
        Bukkit.unloadWorld(this.dim, true);
        this.dim = null;
    }



    public World getWorld() {
        return this.dim;
    }
}
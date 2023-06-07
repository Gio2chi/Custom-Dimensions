package it.angaronigiovanni.pvparena.dimensions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Dimension {
    private String name;
    private World dim;
    private WorldCreator worldCreator;
    private Path folder;
    private boolean isLoaded = false;


    public Dimension (String dimName, Path dimsPath) throws IOException {
        this.name = dimName;
        this.folder = dimsPath;

        if(!Files.exists(this.folder)) Files.createDirectories(this.folder);
        if(!Files.exists(new File(this.folder.toString() + this.name).toPath())) throw new Error(this.name + " doesnt exist", null);

        this.worldCreator = this.create();
    }

    private WorldCreator create () {
        File worldFolder = new File(this.folder.toFile() + "/" + this.name); // Modify as per your world folder path
        return new WorldCreator(worldFolder.getName());
    }
    
    public void load () {
        this.isLoaded = true;
        this.dim = this.worldCreator.createWorld(); // Load the world
    }
    public World getWorld () {
        if(this.isLoaded) return this.dim;
        throw new Error("World not loaded", null);
    }
}

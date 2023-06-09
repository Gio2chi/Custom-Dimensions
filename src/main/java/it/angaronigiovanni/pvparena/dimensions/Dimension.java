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

    public Dimension ( String dimName) throws IOException {

        this.name = dimName;

        if(!Files.exists(new File( this.name ).toPath())) 
            throw new Error(this.name + " doesnt exist", null);

        this.worldCreator = this.create();
    }
    public Dimension ( String dimName,  Path dimsPath) throws IOException {
        this.name = dimName;

        if(!Files.exists(dimsPath)) 
            Files.createDirectories(dimsPath);
        
        File world = new File( dimsPath.toString() + "/" + this.name );

        if(!Files.exists(world.toPath())) 
            throw new Error(this.name + " doesnt exist", null);
        
        this.worldCreator = this.create(dimsPath);
    }

    private WorldCreator create () {
        return new WorldCreator(this.name);
    }
    private WorldCreator create (Path folder) {
        return new WorldCreator( folder.toString() + "/" + this.name);
    }
    
    public void load () {
        if (this.dim == null) {
            this.dim = this.worldCreator.createWorld(); // Load the world
        }
    }
    public World getWorld () {
        if( this.dim != null ) return this.dim;
        throw new Error("World not loaded", null);
    }
}

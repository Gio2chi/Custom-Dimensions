package it.angaronigiovanni.pvparena.commands;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Minigames {
    private World[] minigames = new World[0];
    private int[] pvpArenasIndexes = new int[0];

    public Minigames(String minigame) {
        WorldCreator world = new WorldCreator(minigame);
        this.minigames[0] = world.createWorld();
    }
    public Minigames(String[] minigames) {
        for (int i = 0; i < minigames.length; i++) {
            WorldCreator world = new WorldCreator(minigames[i]);
            this.minigames[i] = world.createWorld();
        }
    }

    public void addMinigame(World minigame) {
        this.minigames[this.minigames.length] = minigame;
        return;
    }
    public void addMinigame(String minigame) {
        WorldCreator world = new WorldCreator(minigame);
        this.minigames[this.minigames.length] = world.createWorld();
        return;
    }

    public World getMinigame(int index) {
        if(minigames[index] == null) throw new Error("Minigame not found", null); 
        return minigames[index];
    }
    public World getMinigame(String name) {
        for(int i=0; i!=minigames.length; i++) 
            if(minigames[i].getName().equalsIgnoreCase(name)) 
                return minigames[i];

        throw new Error("Minigame not found", null);
    }
}

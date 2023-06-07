package it.angaronigiovanni.pvparena.dimensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinigameList extends ArrayList<Minigame>{
    
    public List<Minigame> subList(MinigameType type) {
        List<Minigame> specificList =  new ArrayList<Minigame>();

        for (Minigame minigame : this) {
            if(minigame.type == type) specificList.add(minigame);
        }

        return specificList;
    }

    public Minigame rand () {
        Random rand = new Random();

        return this.get(rand.nextInt(this.size()));
    }
    public Minigame rand (MinigameType type) {
        List<Minigame> tmp = this.subList(type);
        
        Random rand = new Random();

        return tmp.get(rand.nextInt(tmp.size()));
    }
}

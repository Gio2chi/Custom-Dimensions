package it.angaronigiovanni.pvparena.dimensions;

import java.io.File;
import java.io.IOException;

public class Minigame extends Dimension{
    public MinigameType type;

    public Minigame (String name, MinigameType type) throws IOException {
        super(name, (new File("minigames/").toPath()));
        this.type = type;
    }
}
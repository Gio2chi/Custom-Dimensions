package it.angaronigiovanni.pvparena.dimensions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationOptions;

public class Minigame extends Dimension{
    public static final File folder = new File("minigames/");
    public int type;

    public Minigame ( String name,  int type) throws IOException {
        super(name, (new File("minigames/").toPath()));
        this.type = type;
    }

    public void loadConfig() {
        // Something like MinigameType.loadDefaultConfig
    }
    public void loadConfig(List<ConfigurationOptions> config) {
    }
}
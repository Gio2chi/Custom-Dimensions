package it.angaronigiovanni.pvparena.dimensions;

public class MinigameType {
    public static final int PVP = 0;
    public static final int BUILD_BATTLE = 1;
    public static final int PARKOUR = 2;
    public static final int SKYWARS = 3;
    public static final int ESCAPE = 4;
    public static final int CTM = 5;
    public static final int PVE = 6;
    public static final int HIDE_AND_SEEK = 7;
    public static final int DROPPER = 8;
    public static final int RPG = 9;
    public static final int FIND_THE_BUTTON = 10;
    public static final int ELYTRA = 11;
    public static final int MISC = 12;
    public static final int SURVIVAL = 13;
    public static String[] types = { "PVP", "BUILD_BATTLE", "PARKOUR", "SKYWARS", "ESCAPE", "CTM", "PVE", "HIDE_AND_SEEK", "DROPPER", "RPG", "FIND_THE_BUTTON", "ELYTA", "MISC", "SURVIVAL"};

    private int type;
    MinigameType(int type) {
        if(!(type < MinigameType.types.length && type > 0)) throw new Error("Minigame type not available", null);
        this.type = type;
    }

    int getType() {
        return this.type;
    }

    public static final int parseType(String type) {
        for( int i=0; i!=MinigameType.types.length; i++ ) 
            if (MinigameType.types[i].equalsIgnoreCase(type)) return i; 
        return MinigameType.types.length - 1;
    }
}

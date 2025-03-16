package com.unknow.entity.data;

public class ParticularCardData extends CardData{

    private final int id;
    public final int COLOUR_TRANSFER = 0;
    public final int DRAW_FOUR_REVERSE = 1;
    public final int DRAW_SIX_REVERSE = 2;
    public final int DRAW_TEN_REVERSE = 3;
    public final int UNTIL_THE_COLOR_IS_CAUGHT = -1;
    public ParticularCardData(String imageName, int id) {
        super(1 ,imageName, null, "BLACK");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

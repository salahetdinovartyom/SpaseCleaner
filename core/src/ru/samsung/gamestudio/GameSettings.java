package ru.samsung.gamestudio;

public class GameSettings {

    //Device
    public static final int SCREEN_WIDTH=720, SCREEN_HEIGHT=1280;
    //Box2D
    public static final float STEP_TIME=1f/60f;
    public static final int VELOCITY_ITERATIONS=6,POSITION_ITERATIONS=6;
    public static final float SCALE = 0.05f;
    //Корабль
    public static final short SHIP_BIT = 2,BONUS_BIT=8;
    public static final int BONUS_COOL_DOWN=10000;
    public static final int SHIP_WIDTH=150,SHIP_HEIGHT=150,SHIP_FORCE_RATIO=10;
    //Мусоp
    public static final short TRASH_BIT = 1;
    public static final int TRASH_VELOSITY=20,TRASH_WIDTH=140,TRASH_HEIGHT=100;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN=1500;
    //Пуля
    public static final int BULLET_VELOSITY=200,SHOOTING_COOL_DOWN=1000,BULLET_WIDTH = 15,BULLET_HEIGHT = 45;
    public static final short BULLET_BIT = 4;

}

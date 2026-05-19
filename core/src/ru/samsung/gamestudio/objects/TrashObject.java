package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Random;

import static ru.samsung.gamestudio.GameSettings.*;

public class TrashObject extends GameObject{
    private int livesLeft;
    private static final int paddingHorizontal=30;
    public TrashObject(int width, int height, String texturePath, World world) {
        super(texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                SCREEN_HEIGHT+height/2,
                width, height,
                TRASH_BIT, world
        );
        body.setLinearVelocity(new Vector2(0,-TRASH_VELOSITY));
        livesLeft=1;
    }
    public boolean isInFrame() {return getY()+height/2>0;}
    @Override
    public void hit() {livesLeft--;}
    public boolean isNotAlive() {return livesLeft <= 0;}
}

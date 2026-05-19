package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Random;

import static ru.samsung.gamestudio.GameSettings.*;

import ru.samsung.gamestudio.screens.GameScreen;

public class BonusObject extends GameObject{
    private int livesLeft;
    private static final int paddingHorizontal=30;
    public BonusObject (int width, int height, String texturePath, World world) {
        super(texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                SCREEN_HEIGHT+height/2,
                width, height,
                BONUS_BIT, world
        );
        body.setLinearVelocity(new Vector2(0,-TRASH_VELOSITY));
        livesLeft=1;

    }
    public boolean isInFrame() {return getY()+height/2>0||getY()+height/2<SCREEN_HEIGHT;}
    public void hit() {
        livesLeft--;
        if (GameScreen.shipObject.livesLeft==2) GameScreen.shipObject.livesLeft=3;
        else if (GameScreen.shipObject.livesLeft==1) GameScreen.shipObject.livesLeft=2;
        if (!GameScreen.shipObject.isBig) {
            GameScreen.shipObject.textureX2();
            GameScreen.shipObject.circleShape.setRadius(Math.max(width,height)*SCALE/2f);
            GameScreen.shipObject.isBig=true;
        }
    }
    public boolean isNotAlive() {return livesLeft <= 0;}

}


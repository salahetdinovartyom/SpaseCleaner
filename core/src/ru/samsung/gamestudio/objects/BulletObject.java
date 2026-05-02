package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static ru.samsung.gamestudio.GameSettings.*;

public class BulletObject extends GameObject {
    boolean wasHit;
    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath,x,y,width,height, BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0,BULLET_VELOSITY));
        body.setBullet(true);
        wasHit=false;
    }
    public boolean hasToBeDestroyed() {
        return wasHit||(getY()-height/2>SCREEN_HEIGHT);
    }
    @Override
    public void hit() {
        wasHit=true;
    }

}

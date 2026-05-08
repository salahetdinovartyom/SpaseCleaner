package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import static ru.samsung.gamestudio.GameSettings.*;

public class ShipObject extends GameObject{
    long lastShotTime;
    int livesLeft;

    public int getLiveLeft() {
        return livesLeft;
    }
    public ShipObject(int x, int y, int width, int height,String texturePath, World world) {
        super(texturePath, x, y, width, height, SHIP_BIT, world);
        body.setLinearDamping(15);
        livesLeft=3;
    }

    private void putInFrame() {
        if (getY()>(SCREEN_HEIGHT/2f-height/2f)) {
            setY(SCREEN_HEIGHT/2-height/2);
        }
        if (getY()<=(height/2f)) {
            setY(height/2);
        }
        if (getX()>(SCREEN_WIDTH+width/2f)) {
            setX(0);
        }
        if (getX()<(-width/2f)) {
            setX(SCREEN_WIDTH);
        }
    }
    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
    public void move(Vector3 vector3) {
        float fx=(vector3.x-getX())*SHIP_FORCE_RATIO,fy=(vector3.y-getY())*SHIP_FORCE_RATIO;
        body.applyForceToCenter(new Vector2(fx, fy),true);
    }
    public boolean needToShoot() {
        if (TimeUtils.millis()-lastShotTime>=SHOOTING_COOL_DOWN) {
            lastShotTime=TimeUtils.millis();
            return true;
        }
        return false;
    }
    @Override
    public void hit() {livesLeft--;}

    public boolean isAlive() {return livesLeft>0;}

}

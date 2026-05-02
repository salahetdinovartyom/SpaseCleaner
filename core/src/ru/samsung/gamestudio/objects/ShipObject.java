package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import static ru.samsung.gamestudio.GameSettings.*;

public class ShipObject extends GameObject{

    public ShipObject(int x, int y, int width, int height,String texturePath, World world) {
        super(texturePath, x, y, width, height, world);
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
}

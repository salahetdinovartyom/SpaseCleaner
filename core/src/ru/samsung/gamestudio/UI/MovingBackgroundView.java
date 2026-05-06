package ru.samsung.gamestudio.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static ru.samsung.gamestudio.GameSettings.*;


public class MovingBackgroundView extends View {
    Texture texture;

    int texture1Y,texture2Y,speed=2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y=0;
        texture2Y=SCREEN_HEIGHT;
        texture=new Texture(pathToTexture);
    }
    public void move() {
        texture1Y-=speed;
        texture2Y-=speed;
        if (texture1Y<=-SCREEN_HEIGHT) texture1Y=SCREEN_HEIGHT;
        if (texture2Y<=-SCREEN_HEIGHT) texture2Y=SCREEN_HEIGHT;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,0,texture1Y,SCREEN_WIDTH,SCREEN_HEIGHT);
        batch.draw(texture,0,texture2Y,SCREEN_WIDTH,SCREEN_HEIGHT);
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}

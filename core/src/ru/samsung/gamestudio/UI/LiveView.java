package ru.samsung.gamestudio.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static ru.samsung.gamestudio.GameResources.*;

public class LiveView extends View{
    Texture texture;
    private static int livePadding=6;
    private String text;
    private int leftLives;
    public LiveView(float x,float y) {
        super(x,y);
        texture=new Texture(LIVE_IMG_PATH);
        this.width=texture.getWidth();
        this.height=texture.getHeight();
        leftLives=0;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if (leftLives>0) batch.draw(texture,x+(texture.getWidth()+livePadding),y,width,height);
        if (leftLives>1) batch.draw(texture,x,y,width,height);
        if (leftLives>2) batch.draw(texture,x+2*(texture.getWidth()+livePadding),y,width,height);
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}

package ru.samsung.gamestudio.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class View implements Disposable {
    float x,y,width,height;

    public View (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public View(float x, float y, float width, float height) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public boolean isHit(float tx, float ty) {
        return false;
    }
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}

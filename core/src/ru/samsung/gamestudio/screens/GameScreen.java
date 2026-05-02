package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.objects.ShipObject;
import static ru.samsung.gamestudio.GameResources.*;
import static ru.samsung.gamestudio.GameSettings.*;


public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame=myGdxGame;
        shipObject =new ShipObject(SCREEN_WIDTH/2,150,
                SHIP_WIDTH,SHIP_HEIGHT,
                SHIP_IMG_PATH,MyGdxGame.world);
    }

    @Override
    public void render(float delta) {
        MyGdxGame.stepWorld();
        handleInput();
        draw();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch=myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            shipObject.move(myGdxGame.touch);
        }
    }
    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        shipObject.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }
}

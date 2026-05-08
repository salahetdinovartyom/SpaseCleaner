package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.ScreenAdapter;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.UI.MovingBackgroundView;

import static ru.samsung.gamestudio.GameResources.*;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame=myGdxGame;
        backgroundView=new MovingBackgroundView(BG_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

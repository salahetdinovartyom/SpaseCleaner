package ru.samsung.gamestudio;

import static ru.samsung.gamestudio.GameSettings.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.screens.GameScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
    public static World world;
    GameScreen gameScreen;
    static float accumulator=0f;

	
	@Override
	public void create () {
        Box2D.init();
        world=new World(new Vector2(0,0),true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,GameSettings.SCREEN_WIDTH,GameSettings.SCREEN_HEIGHT);
        gameScreen=new GameScreen(this);
        setScreen(gameScreen);


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    public static void stepWorld() {
        float delta= Gdx.graphics.getDeltaTime();
        accumulator+=delta;
        if (accumulator>= STEP_TIME) {
            accumulator-= STEP_TIME;
            world.step(STEP_TIME,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        }
    }
}

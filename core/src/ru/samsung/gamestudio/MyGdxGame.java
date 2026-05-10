package ru.samsung.gamestudio;

import static ru.samsung.gamestudio.GameSettings.*;
import static ru.samsung.gamestudio.GameResources.FONT_PATH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.managers.AudioManager;
import ru.samsung.gamestudio.screens.GameScreen;
import ru.samsung.gamestudio.screens.MenuScreen;
import ru.samsung.gamestudio.screens.SettingsScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
    public static World world;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settingsScreen;
    static float accumulator=0f;
    public Vector3 touch;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;
    public BitmapFont largeWhiteFont;
    public AudioManager audioManager;

	
	@Override
	public void create () {
        Box2D.init();
        world=new World(new Vector2(0,0),true);

        commonWhiteFont=FontBuilder.generate(24, Color.WHITE,FONT_PATH);
        commonBlackFont=FontBuilder.generate(24, Color.BLACK,FONT_PATH);
        largeWhiteFont=FontBuilder.generate(48, Color.WHITE,FONT_PATH);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,GameSettings.SCREEN_WIDTH,GameSettings.SCREEN_HEIGHT);

        audioManager=new AudioManager();

        gameScreen=new GameScreen(this);
        menuScreen=new MenuScreen(this);
        settingsScreen=new SettingsScreen(this);
        setScreen(menuScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    public static void stepWorld() {
        float delta= Gdx.graphics.getDeltaTime();
        accumulator+=Math.min(delta,0.25f);
        if (accumulator>= STEP_TIME) {
            accumulator-= STEP_TIME;
            world.step(STEP_TIME,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        }
    }
}

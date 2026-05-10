package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.UI.ButtonView;
import ru.samsung.gamestudio.UI.ImageView;
import ru.samsung.gamestudio.UI.MovingBackgroundView;
import ru.samsung.gamestudio.UI.TextView;
import static ru.samsung.gamestudio.GameResources.*;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView settingsBlackoutView;
    ButtonView menuButtonView;
    TextView soundButton,musicButton, clearRecordsButton;
    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame=myGdxGame;
        backgroundView=new MovingBackgroundView(BG_IMG_PATH);
        titleTextView=new TextView(myGdxGame.largeWhiteFont,256,956,"Settings");
        settingsBlackoutView=new ImageView(85,365,BLACKOUT_MIDDLE_IMG_PATH);
        menuButtonView=new ButtonView(280,150,160,70,myGdxGame.commonBlackFont,BUTTON_BACKGROUND_SHORT_IMG_PATH,"Return");
        soundButton=new TextView(myGdxGame.commonWhiteFont,173,599,"Sound: " +"ON");
        musicButton=new TextView(myGdxGame.commonWhiteFont,173,658,"Music: "+"ON");
        clearRecordsButton =new TextView(myGdxGame.commonWhiteFont,173,540,"Clear records");
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleTextView.draw(myGdxGame.batch);
        settingsBlackoutView.draw(myGdxGame.batch);
        menuButtonView.draw(myGdxGame.batch);
        soundButton.draw(myGdxGame.batch);
        musicButton.draw(myGdxGame.batch);
        clearRecordsButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        titleTextView.dispose();
        backgroundView.dispose();
        settingsBlackoutView.dispose();
        musicButton.dispose();
        menuButtonView.dispose();
        soundButton.dispose();
        clearRecordsButton.dispose();
    }
    private String translateStateToText(boolean state) {
        return state ? "ON":"OFF";
    }
    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch=myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            if (menuButtonView.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (clearRecordsButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                clearRecordsButton.setText("Clear records (cleared)");
            }if (musicButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                myGdxGame.audioManager.isMusicOn= !myGdxGame.audioManager.isMusicOn;
                musicButton.setText("Music: "+translateStateToText(myGdxGame.audioManager.isMusicOn));
                myGdxGame.audioManager.updateMusicFlag();
            }if (soundButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                myGdxGame.audioManager.isSoundOn= !myGdxGame.audioManager.isSoundOn;
                soundButton.setText("Sound: "+translateStateToText(myGdxGame.audioManager.isSoundOn));
            }
        }
    }
}

package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.UI.RecordsListView;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameState;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.UI.ButtonView;
import ru.samsung.gamestudio.UI.ImageView;
import ru.samsung.gamestudio.UI.LiveView;
import ru.samsung.gamestudio.UI.MovingBackgroundView;
import ru.samsung.gamestudio.UI.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.ShipObject;
import ru.samsung.gamestudio.objects.TrashObject;

import static ru.samsung.gamestudio.GameResources.*;
import static ru.samsung.gamestudio.GameSettings.*;

import java.util.ArrayList;


public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession gameSession;
    ContactManager contactManager;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;
    ImageView fullBlackoutView;
    ButtonView homeButton,continueButton;
    TextView pauseTextView;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame=myGdxGame;
        gameSession=new GameSession();
        contactManager=new ContactManager(MyGdxGame.world);

        trashArray=new ArrayList<>();
        bulletArray=new ArrayList<>();

        shipObject =new ShipObject(SCREEN_WIDTH/2,150,
                SHIP_WIDTH,SHIP_HEIGHT,
                SHIP_IMG_PATH,MyGdxGame.world);
        backgroundView=new MovingBackgroundView(BG_IMG_PATH);
        topBlackoutView=new ImageView(0,1180,BLACKOUT_TOP_IMG_PATH);
        liveView=new LiveView(305,1215);
        scoreTextView=new TextView(myGdxGame.commonWhiteFont,50,1215);
        pauseButton=new ButtonView(605,1200,46,54,PAUSE_IMG_PATH);
        fullBlackoutView=new ImageView(0,0,FULL_BLACKOUT_IMG_PATH);
        homeButton=new ButtonView(138,695,200,70,myGdxGame.commonBlackFont,BUTTON_BACKGROUND_SHORT_IMG_PATH,"Home");
        continueButton=new ButtonView(393,695,200,70,myGdxGame.commonBlackFont, BUTTON_BACKGROUND_SHORT_IMG_PATH,"Continue");
        pauseTextView=new TextView(myGdxGame.largeWhiteFont,282,842,"Pause");
        recordsTextView=new TextView(myGdxGame.largeWhiteFont,206,842,"Last records");
        recordsListView=new RecordsListView(myGdxGame.commonWhiteFont,690);
        homeButton2=new ButtonView(280,365,160,70,myGdxGame.commonBlackFont,BUTTON_BACKGROUND_SHORT_IMG_PATH,"Home");
    }

    @Override
    public void show () {
        restartGame();
    }

    @Override
    public void render(float delta) {

        handleInput();
        if (gameSession.state==GameState.PLAYING) {

            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(TRASH_WIDTH, TRASH_HEIGHT,
                        TRASH_IMG_PATH, MyGdxGame.world);
                trashArray.add(trashObject);
            }
            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        BULLET_WIDTH, BULLET_HEIGHT,
                        BULLET_IMG_PATH, MyGdxGame.world);
                bulletArray.add(laserBullet);

                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            updateTrash();
            updateBullets();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            liveView.setLeftLives(shipObject.getLiveLeft());

            MyGdxGame.stepWorld();
        }
        draw();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
        for (TrashObject trash : trashArray) trash.dispose();
        for (BulletObject bullet : bulletArray) bullet.dispose();
        backgroundView.dispose();
        topBlackoutView.dispose();
        backgroundView.dispose();
        topBlackoutView.dispose();
        liveView.dispose();
        scoreTextView.dispose();
        pauseButton.dispose();
        fullBlackoutView.dispose();
        homeButton.dispose();
        continueButton.dispose();
        pauseTextView.dispose();
        recordsListView.dispose();
        recordsTextView.dispose();
        homeButton2.dispose();

    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch=myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    shipObject.move(myGdxGame.touch);
                    break;
                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y))
                        gameSession.resumeGame();
                    if (homeButton.isHit(myGdxGame.touch.x,myGdxGame.touch.y))
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    break;
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x,myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }
    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        if (gameSession.state== GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        }else if (gameSession.state==GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();
    }
    private void updateTrash() {
        for (int i=0;i<trashArray.size();i++) {
            boolean hasToBeDestroyed=!trashArray.get(i).isInFrame()||!trashArray.get(i).isAlive();
            if (!trashArray.get(i).isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.2f);
            }
            if (hasToBeDestroyed) {
                MyGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }
    private void updateBullets() {
        for (int i=0;i<bulletArray.size();i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                MyGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    private void restartGame() {
        for (int i=0; i<trashArray.size(); i++) {
            MyGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }
        if (shipObject!=null) {
            MyGdxGame.world.destroyBody(shipObject.body);
        }
        shipObject = new ShipObject(
                SCREEN_WIDTH / 2, 150,
                SHIP_WIDTH, SHIP_HEIGHT,
                SHIP_IMG_PATH,
                MyGdxGame.world
        );
        bulletArray.clear();
        gameSession.startGame();
    }
}

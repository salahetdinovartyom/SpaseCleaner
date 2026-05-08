package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.ContactManager;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.UI.ButtonView;
import ru.samsung.gamestudio.UI.ImageView;
import ru.samsung.gamestudio.UI.LiveView;
import ru.samsung.gamestudio.UI.MovingBackgroundView;
import ru.samsung.gamestudio.UI.TextView;
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
    }

    @Override
    public void show () {
        gameSession.startGame();
    }

    @Override
    public void render(float delta) {
        MyGdxGame.stepWorld();
        handleInput();
        backgroundView.move();
        liveView.setLeftLives(shipObject.getLiveLeft());
        scoreTextView.setText("Score: "+52);

        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject= new TrashObject(TRASH_WIDTH,TRASH_HEIGHT,
                    TRASH_IMG_PATH,MyGdxGame.world);
            trashArray.add(trashObject);
        }
        if (shipObject.needToShoot()) {
            BulletObject laserBullet=new BulletObject(shipObject.getX(),shipObject.getY()+shipObject.height/2,
                    BULLET_WIDTH,BULLET_HEIGHT,
                    BULLET_IMG_PATH,MyGdxGame.world);
            bulletArray.add(laserBullet);
        }
        if (!shipObject.isAlive()) {
            System.out.println("Game over!");
        }

        updateTrash();
        updateBullets();

        draw();
    }

    @Override
    public void dispose() {
        shipObject.dispose();
        for (TrashObject trash : trashArray) trash.dispose();
        for (BulletObject bullet : bulletArray) bullet.dispose();
        backgroundView.dispose();
        topBlackoutView.dispose();
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

        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
    private void updateTrash() {
        for (int i=0;i<trashArray.size();i++) {
            if (!trashArray.get(i).isInFrame()||!trashArray.get(i).isAlive()) {
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
}
